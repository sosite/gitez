package com.socros.android.app.gitez.contentsearch.di

import com.socros.android.app.gitez.contentsearch.view.ContentListActivity
import com.socros.android.app.gitez.contentsearch.view.ContentListFragment
import com.socros.android.lib.androidcore.di.AppModule
import com.socros.android.lib.androidcore.di.AppModuleInjector
import com.socros.android.lib.androidcore.di.AppModuleSubComponent
import com.socros.android.lib.util.createFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@Retention
annotation class ContentSearchScope

@ContentSearchScope
@Component(
		modules = [ContentListActivityModule::class, ContentSearchModule::class, AppModule::class],
		dependencies = [AppModuleSubComponent::class])
interface ContentSearchActivityComponent : AppModuleInjector<ContentListActivity> {
	@Component.Builder
	abstract class Builder : AppModuleInjector.Builder<ContentListActivity>()
}

@ContentSearchScope
@Component(
		modules = [ContentSearchFragmentModule::class, ContentSearchModule::class, AppModule::class],
		dependencies = [AppModuleSubComponent::class])
interface ContentListFragmentComponent : AppModuleInjector<ContentListFragment> {
	@Component.Builder
	abstract class Builder : AppModuleInjector.Builder<ContentListFragment>()
}


@Module
class ContentListActivityModule {
	@Provides
	@ContentSearchScope
	internal fun provideContentListFragment() = createFragment<ContentListFragment>()
}

@Module
class ContentSearchFragmentModule {
	@Provides
	internal fun provideContentListActivity(fragment: ContentListFragment) =
			fragment.activity as ContentListActivity
}
