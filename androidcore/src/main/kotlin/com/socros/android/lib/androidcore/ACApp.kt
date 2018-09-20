package com.socros.android.lib.androidcore

import com.socros.android.lib.androidcore.di.AppComponent
import com.socros.android.lib.androidcore.di.DaggerAppComponent
import dagger.android.support.DaggerApplication

abstract class ACApp : DaggerApplication() {

	private lateinit var appComponent: AppComponent

	override fun applicationInjector() = appComponent
	internal fun provideAppModuleSubComponent() = appComponent.appModuleSubComponentBuilder.build()

	override fun onCreate() {
		appComponent = DaggerAppComponent.builder().create(this) as AppComponent
		super.onCreate()
	}

}
