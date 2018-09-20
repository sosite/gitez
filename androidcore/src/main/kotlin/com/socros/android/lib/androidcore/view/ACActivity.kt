package com.socros.android.lib.androidcore.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.socros.android.lib.androidcore.ACApp
import com.socros.android.lib.androidcore.di.AppProvider

abstract class ACActivity : AppCompatActivity(), AppProvider {

	/**
	 * You can pass a `null` value if your activity doesn't need to draw itself,
	 * eg. when you set activity theme to `"@android:style/Theme.NoDisplay"` in manifest
	 */
	protected abstract val layoutResId: Int?

	final override fun provideApp() = application as ACApp

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		layoutResId?.let {
			setContentView(it)
		}
	}
}
