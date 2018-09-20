package com.socros.android.app.gitez.view.launcher

import androidx.lifecycle.ViewModel
import com.socros.android.lib.androidcore.di.AppModuleInjector
import com.socros.android.lib.androidcore.di.AppModuleSubComponent
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelFactory
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelKey
import com.socros.android.lib.androidcore.di.viewmodel.create
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Scope

@Scope
@Retention
annotation class SplashScope

@SplashScope
@Component(modules = [SplashModule::class], dependencies = [AppModuleSubComponent::class])
interface SplashComponent : AppModuleInjector<SplashActivity> {

	@Component.Builder
	abstract class Builder : AppModuleInjector.Builder<SplashActivity>()
}

@Module
class SplashModule {
	@Provides
	@IntoMap
	@ViewModelKey(SplashViewModel::class)
	fun provideSplashViewModel(): ViewModel = SplashViewModel()

	@Provides
	fun createSplashViewModel(target: SplashActivity, factory: ViewModelFactory) =
			factory.create<SplashViewModel>(target)

}
