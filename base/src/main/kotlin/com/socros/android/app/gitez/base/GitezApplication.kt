package com.socros.android.app.gitez.base

import com.socros.android.app.gitez.base.di.DaggerApplicationComponent
import com.socros.android.lib.tim.Tim
import dagger.android.support.DaggerApplication

class GitezApplication : DaggerApplication() {

	init {
		Tim.init(BuildConfig.DEBUG)
	}

	override fun applicationInjector() =
			DaggerApplicationComponent.builder().application(this).build()

}
