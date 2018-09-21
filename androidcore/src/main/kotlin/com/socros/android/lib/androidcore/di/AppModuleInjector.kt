package com.socros.android.lib.androidcore.di

import dagger.android.AndroidInjector

/**
 * https://medium.com/@luigi.papino/dagger2-for-modular-architecture-332e1250a85f
 */
interface AppModuleInjector<T : AppProvider> : AndroidInjector<T> {

	abstract class Builder<T : AppProvider> : AndroidInjector.Builder<T>() {

		abstract operator fun plus(component: AppModuleSubComponent): Builder<T>

		fun inject(objectToInject: T) {
			plus(objectToInject.provideApp().provideAppModuleSubComponent())
			create(objectToInject)
					.inject(objectToInject)
		}
	}
}
