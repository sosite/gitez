package com.socros.android.lib.androidcore.view.launcher

import android.app.Activity
import android.os.Bundle

/**
 * Fist app activity that decide which activity should be run as first depends on user logged in status.
 *
 * Use with `android:theme="@android:style/Theme.NoDisplay"`
 */
abstract class ACLauncherActivity : Activity(), Launcher {

	override fun onCreate(savedInstanceState: Bundle?) {
		startActivity(provideNextActivity())
		finish()
		super.onCreate(savedInstanceState)
	}

}
