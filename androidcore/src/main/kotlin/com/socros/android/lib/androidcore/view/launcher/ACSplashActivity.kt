package com.socros.android.lib.androidcore.view.launcher

import android.os.Bundle
import com.socros.android.lib.androidcore.view.ACActivity
import io.reactivex.disposables.Disposable

/**
 * Fist app activity that decide which activity should be run as first depends eg. on user logged in status.
 */
abstract class ACSplashActivity : ACActivity(), Launcher {

	/**
	 * You can pass a `null` value when you set custom drawable as activity theme in `android:windowBackground`
	 */
	abstract override val layoutResId: Int?

	abstract val viewModel: ACSplashViewModel<*>

	private lateinit var disposable: Disposable

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (firstTimeCreated(savedInstanceState)) viewModel.initialize()
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
