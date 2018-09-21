package com.socros.android.lib.androidcore.view.launcher

import androidx.lifecycle.ViewModel
import com.socros.android.lib.util.composeAsync
import com.socros.android.lib.util.delayToAtLeast
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class ACSplashViewModel<T> : ViewModel() {

	protected abstract val splashMinVisibilityTime: Int

	private val finishedSubject = BehaviorSubject.create<Any>()
	private lateinit var disposable: Disposable

	val finished: Observable<*>
		get() = finishedSubject

	internal fun initialize() {
		disposable = provideObservableOperation()
				?.delayToAtLeast(splashMinVisibilityTime.toLong(), MILLISECONDS)
				?.composeAsync()
				?.subscribeOperation()

				?: Observable.timer(splashMinVisibilityTime.toLong(), MILLISECONDS)
				.subscribe { onSplashStartOperationFinished() }
	}

	override fun onCleared() {
		disposable.dispose()
	}

	@Suppress("MemberVisibilityCanBePrivate")
	protected fun setFinished() {
		finishedSubject.onNext(Unit)
	}

	protected open fun provideObservableOperation(): Observable<T>? = null

	protected open fun Observable<T>.subscribeOperation(): Disposable {
		throw NotImplementedError(
				"If you override `provideObservableOperation` you must also override `subscribeOperation`.\n" +
						"Remember to call `onSplashStartOperationFinished()` method.")
	}

	protected open fun onSplashStartOperationFinished() {
		setFinished()
	}

}
