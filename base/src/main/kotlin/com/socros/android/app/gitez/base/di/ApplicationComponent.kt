package com.socros.android.app.gitez.base.di

import android.app.Application
import com.socros.android.app.gitez.base.GitezApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ActivityBindingModule::class, AndroidSupportInjectionModule::class])
interface ApplicationComponent : AndroidInjector<GitezApplication> {

	@Component.Builder
	interface Builder {
		@BindsInstance
		fun application(application: Application): Builder

		fun build(): ApplicationComponent
	}

}
