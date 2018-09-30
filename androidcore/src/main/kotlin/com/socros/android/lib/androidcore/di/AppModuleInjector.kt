package com.socros.android.lib.androidcore.di

import android.app.Application
import dagger.BindsInstance
import dagger.android.AndroidInjector

/**
 * @see <a href="https://medium.com/@luigi.papino/dagger2-for-modular-architecture-332e1250a85f">Dagger2 for Modular Architecture</a>
 */
interface AppModuleInjector<T : AppProvider> : AndroidInjector<T> {

	abstract class Builder<T : AppProvider> : AndroidInjector.Builder<T>() {

		@BindsInstance
		abstract operator fun plus(application: Application): Builder<T>

		abstract operator fun plus(component: AppModuleSubComponent): Builder<T>

		fun inject(objectToInject: T) {
			objectToInject.provideApp().also {
				plus(it)
				plus(it.provideAppModuleSubComponent())
			}

			create(objectToInject).inject(objectToInject)
		}
	}
}
