package com.socros.android.lib.androidcore.di

import com.socros.android.lib.androidcore.ACApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<ACApp> {

	val appModuleSubComponentBuilder: AppModuleSubComponent.Builder

	@Component.Builder
	abstract class Builder : AndroidInjector.Builder<ACApp>() {
		abstract override fun build(): AppComponent
	}

}
