package com.socros.android.app.base.view.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.socros.android.lib.util.composeAsync
import com.socros.android.lib.util.delayToAtLeast
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * Fist app activity that decide which activity should be run as first depends on user logged in status.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class CoreSplashActivity<T> : AppCompatActivity(), Launcher {

	object SplashStartOperation {
		private val subject by lazy { BehaviorSubject.create<Boolean>() }

		val finished: Observable<Boolean>
			get() = subject.filter { it }

		fun setFinished() {
			subject.onNext(true)
		}

		fun setConsumed() {
			subject.onNext(false)
		}
	}

	/**
	 * You can pass null when you set view as theme window background
	 */
	protected abstract val layoutResId: Int?
	protected abstract val splashMinVisibilityTime: Int

	private lateinit var disposable: Disposable

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		layoutResId?.let {
			setContentView(it)
		}

		if (savedInstanceState == null) {
			provideObservableOperation()
					?.delayToAtLeast(splashMinVisibilityTime.toLong(), MILLISECONDS)
					?.composeAsync()
					?.subscribeOperation()

					?: Observable.timer(splashMinVisibilityTime.toLong(), MILLISECONDS)
							.subscribe { onSplashStartOperationFinished() }
		}
	}

	override fun onResume() {
		super.onResume()
		disposable = SplashStartOperation.finished.subscribe {
			SplashStartOperation.setConsumed()
			launchProperActivity()
		}
	}

	override fun onPause() {
		disposable.dispose()
		super.onPause()
	}

	protected open fun provideObservableOperation(): Observable<T>? = null

	protected open fun Observable<T>.subscribeOperation(): Disposable {
		throw NotImplementedError(
				"If you override `provideObservableOperation` you must also override `subscribeOperation`.\n" +
						"Remember to call `onSplashStartOperationFinished()` method.")
	}

	protected fun onSplashStartOperationFinished() {
		SplashStartOperation.setFinished()
	}

	protected open fun launchProperActivity() {
		startActivity(provideNextActivity())
		finish()
	}

}
