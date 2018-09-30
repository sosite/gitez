package com.socros.android.app.gitez.contentdetails.di

import com.socros.android.app.gitez.contentdetails.view.UserDetailsActivity
import com.socros.android.lib.androidcore.di.AppModule
import com.socros.android.lib.androidcore.di.AppModuleInjector
import com.socros.android.lib.androidcore.di.AppModuleSubComponent
import dagger.Component
import javax.inject.Scope

@Scope
@Retention
annotation class ContentDetailsScope

@ContentDetailsScope
@Component(
		modules = [UserDetailsViewModelModule::class, AppModule::class],
		dependencies = [AppModuleSubComponent::class])
interface UserDetailsActivityComponent : AppModuleInjector<UserDetailsActivity> {
	@Component.Builder
	abstract class Builder : AppModuleInjector.Builder<UserDetailsActivity>()
}
