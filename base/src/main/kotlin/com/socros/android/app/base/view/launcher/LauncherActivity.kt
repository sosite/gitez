package com.socros.android.app.base.view.launcher

import android.app.Activity
import android.os.Bundle

/**
 * Fist app activity that decide which activity should be run as first depends on user logged in status.
 *
 * Use with `android:theme="@android:style/Theme.NoDisplay"`
 */
abstract class LauncherActivity : Activity(), Launcher {

	override fun onCreate(savedInstanceState: Bundle?) {
		startActivity(provideNextActivity())
		finish()
		super.onCreate(savedInstanceState)
	}

}
