package com.socros.android.lib.androidcore.view.launcher

import android.os.SystemClock
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
	private var disposable: Disposable? = null

	val finished: Observable<*>
		get() = finishedSubject

	internal fun initialize(initStartTime: Long) {
		if (disposable == null) {
			val minVisibilityTime = initStartTime - SystemClock.elapsedRealtime() + splashMinVisibilityTime
			disposable = provideObservableOperation()
					?.delayToAtLeast(minVisibilityTime, MILLISECONDS)
					?.composeAsync()
					?.subscribeOperation()

					?: Observable.timer(minVisibilityTime, MILLISECONDS)
					.subscribe { onSplashStartOperationFinished() }
		}
	}

	override fun onCleared() {
		disposable!!.dispose()
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
