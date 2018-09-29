package com.socros.android.lib.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.room.EmptyResultSetException
import com.socros.android.lib.repository.Resource.LoadingInProgress
import com.socros.android.lib.repository.Resource.NetworkError
import com.socros.android.lib.repository.Resource.ServerError
import com.socros.android.lib.repository.Resource.Success
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.HttpException
import java.io.IOException

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 * You can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 * @param <RequestType>
 * @param <ResultType>
 */
abstract class NetworkBoundResource<RequestType, ResultType> @MainThread constructor() {

	private val subject = BehaviorSubject.create<Resource<ResultType?>>()
	val result: Observable<Resource<ResultType?>> = subject.switchMap {
		if (resetInProgress) Observable.never()
		else Observable.just(it)
	}

	private var compositeDisposable = CompositeDisposable()
	private var resetInProgress = false

	fun initialize() {
		@Suppress("ConvertReferenceToLambda")
		subject.doOnDispose(compositeDisposable::dispose)

		loadFromDb()
				.subscribeOn(Schedulers.io())
				.subscribe { dbData: ResultType?, throwable: Throwable? ->
					when {
						shouldFetch(dbData) ->
							fetchFromNetwork(dbData)

						dbData != null || throwable is EmptyResultSetException ->
							setValue(Success(dbData))

						else -> throw throwable!!
					}
				}
				.addTo(compositeDisposable)
	}

	fun reset() {
		resetInProgress = true
		compositeDisposable.dispose()
		compositeDisposable = CompositeDisposable()
		initialize()
	}

	/**
	 * A more advanced version of [Observable.distinctUntilChanged],
	 * where we also corresponds to the [resetInProgress] flag and reset them
	 */
	@MainThread
	private fun <T : Resource<ResultType?>> setValue(newValue: T) {
		if (resetInProgress || subject.value != newValue) {
			// the same item will be emitted after reset to propagate the old value to the current observables
			resetInProgress = false
			subject.onNext(newValue)
		}
	}

	private fun fetchFromNetwork(oldDbData: ResultType?) {
		setValue(LoadingInProgress(oldDbData))

		createCall()
				.observeOn(Schedulers.io())
				.doOnSuccess { networkData -> saveCallResult(networkData) }
				.flatMap {
					loadFromDb().map { newDbData ->
						Success(newDbData) as Resource<ResultType>
					}
				}
				.onErrorResumeNext { throwable ->
					loadFromDb()
							.map { Optional(it) }
							.onErrorReturn {
								if (it is EmptyResultSetException) Optional(null)
								else throw it
							}
							.map { newDbData ->
								when (throwable) {
									is IOException -> NetworkError(newDbData.value)
									is HttpException -> ServerError(newDbData.value)
									else -> throw throwable
								}
							}
				}
				.subscribe(::setValue)
				.addTo(compositeDisposable)
	}

	/**
	 * Called to get the cached data from the database.
	 */
	@MainThread
	protected abstract fun loadFromDb(): Single<ResultType>

	/**
	 * Called with the data in the database to decide whether to fetch potentially updated data from the network.
	 */
	@MainThread
	abstract fun shouldFetch(data: ResultType?): Boolean

	/**
	 * Called to create the API call.
	 */
	@MainThread
	protected abstract fun createCall(): Single<RequestType>

	/**
	 * Called to save the subject of the API response into the database.
	 */
	@WorkerThread
	protected abstract fun saveCallResult(result: RequestType)

	/**
	 * Called when the fetch fails. The child class may want to reset components like rate limiter.
	 */
	protected open fun onFetchFailed() {}

	private class Optional<T>(var value: T?)

}
