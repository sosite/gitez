package com.socros.android.app.gitez.base.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.socros.android.lib.androidcore.view.ACActivity
import com.socros.android.lib.util.ApiVersion.V26_OREO
import com.socros.android.lib.util.currentApi
import com.socros.android.lib.util.inTransaction

/**
 * Activity that handle light bottom navigation bar programmatically on Android Oreo V26
 */
abstract class BaseActivity : ACActivity() {
	@SuppressLint("InlinedApi")
	override fun onCreate(savedInstanceState: Bundle?) {
		if (currentApi() == V26_OREO) {
			findViewById<View>(android.R.id.content).systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
		}
		super.onCreate(savedInstanceState)
	}

	protected fun addFragment(savedInstanceState: Bundle?, fragmentContainerId: Int, fragmentBuilder: () -> Fragment) =
			savedInstanceState
					?: supportFragmentManager.inTransaction { add(fragmentContainerId, fragmentBuilder()) }

}
