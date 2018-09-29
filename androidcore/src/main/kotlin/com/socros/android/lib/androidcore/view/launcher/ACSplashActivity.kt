package com.socros.android.lib.androidcore.view.launcher

import android.os.Bundle
import android.os.SystemClock
import com.socros.android.lib.androidcore.view.ACActivity
import io.reactivex.disposables.Disposable

/**
 * Fist app activity that decide which activity should be run as first depends eg. on user logged in status.
 */
abstract class ACSplashActivity : ACActivity(), Launcher {

	companion object {
		private const val STATE_INIT_START_TIME = "stateInitStartTime"
	}

	/**
	 * You can pass a `null` value when you set custom drawable as activity theme in `android:windowBackground`
	 */
	abstract override val layoutResId: Int?

	abstract val viewModel: ACSplashViewModel<*>
	private lateinit var disposable: Disposable

	private var initStartTime: Long = -1

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initStartTime = savedInstanceState?.getLong(STATE_INIT_START_TIME) ?: SystemClock.elapsedRealtime()
		viewModel.initialize(initStartTime)
	}

	override fun onSaveInstanceState(outState: Bundle) {
		outState.putLong(STATE_INIT_START_TIME, initStartTime)
		super.onSaveInstanceState(outState)
	}

	override fun onResume() {
		super.onResume()
		disposable = viewModel.finished.subscribe {
			launchProperActivity()
		}
	}

	override fun onPause() {
		disposable.dispose()
		super.onPause()
	}

	protected open fun launchProperActivity() {
		startActivity(provideNextActivity())
		finish()
	}

}
