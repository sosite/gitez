package com.socros.android.app.base.view.launcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.socros.android.lib.util.composeAsync
import com.socros.android.lib.util.delayToAtLeast
import io.reactivex.Observable
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * Fist app activity that decide which activity should be run as first depends on user logged in status.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class SplashActivity<T> : AppCompatActivity(), Launcher {

	/**
	 * You can pass null when you set view as theme window background
	 */
	protected abstract val layoutResId: Int?

	protected abstract val splashMinVisibilityTime: Int

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		layoutResId?.let {
			setContentView(it)
		}

		provideObservableOperation()
				?.delayToAtLeast(splashMinVisibilityTime.toLong(), MILLISECONDS)
				?.composeAsync()
				?.subscribeOperation()

				?: Observable.timer(splashMinVisibilityTime.toLong(), MILLISECONDS)
						.subscribe { launchProperActivity() }
	}

	protected open fun provideObservableOperation(): Observable<T>? = null

	protected open fun Observable<T>.subscribeOperation() {
		throw NotImplementedError(
				"If you override `provideObservableOperation` you must also override `subscribeOperation`")
	}

	protected open fun launchProperActivity() {
		startActivity(provideNextActivity())
		finish()
	}

}
