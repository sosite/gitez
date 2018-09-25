package com.socros.android.lib.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.socros.android.lib.repository.Resource.LoadingInProgress
import com.socros.android.lib.repository.Resource.NetworkError
import com.socros.android.lib.repository.Resource.ServerError
import com.socros.android.lib.repository.Resource.Success
import com.socros.android.lib.util.toForeverObservable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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

	private val subject = BehaviorSubject.create<Resource<out ResultType?>>()
	val result: Observable<Resource<out ResultType?>> = subject

	init {
		@Suppress("LeakingThis")
		val dbSource = loadFromDb().toForeverObservable()

		val disposable: Disposable = dbSource.share()
				.map {
					val data = it.data
					if (data == null || shouldFetch(data)) {
						// if doesn't have a proper data at all or should fetch new
						fetchFromNetwork(data)
						LoadingInProgress(data)

					} else Success(data)
				}
				.subscribe(subject::onNext)

		subject.doOnDispose(disposable::dispose)
	}

	private fun fetchFromNetwork(dbData: ResultType?) {
		val disposable: Disposable = createCall()
				.observeOn(Schedulers.io())
				.doOnNext { saveCallResult(processResponse(it)) }
				.map { true }
				.observeOn(AndroidSchedulers.mainThread())
				.onErrorReturn {
					onFetchFailed()
					when (it) {
						is IOException -> subject.onNext(NetworkError(dbData))
						is HttpException -> subject.onNext(ServerError(dbData))
					}
					false
				}
				.subscribe()

		subject.doOnDispose(disposable::dispose)
	}

	/**
	 * Called to get the cached data from the database.
	 */
	@MainThread
	protected abstract fun loadFromDb(): LiveData<ResultType?>

	/**
	 * Called with the data in the database to decide whether to fetch potentially updated data from the network.
	 */
	@MainThread
	abstract fun shouldFetch(data: ResultType): Boolean

	/**
	 * Called to create the API call.
	 */
	@MainThread
	protected abstract fun createCall(): Observable<RequestType>

	@WorkerThread
	protected open fun processResponse(response: RequestType) = response

	/**
	 * Called to save the subject of the API response into the database.
	 */
	@WorkerThread
	protected abstract fun saveCallResult(result: RequestType)

	/**
	 * Called when the fetch fails. The child class may want to reset components like rate limiter.
	 */
	protected open fun onFetchFailed() {}

}
