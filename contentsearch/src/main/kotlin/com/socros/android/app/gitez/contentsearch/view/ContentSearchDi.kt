package com.socros.android.app.gitez.contentsearch.view

import androidx.lifecycle.ViewModel
import com.socros.android.lib.androidcore.di.AppModuleInjector
import com.socros.android.lib.androidcore.di.AppModuleSubComponent
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelFactory
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelKey
import com.socros.android.lib.androidcore.di.viewmodel.create
import com.socros.android.lib.util.createFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Scope

@Scope
@Retention
annotation class ContentSearchScope

@ContentSearchScope
@Component(
		modules = [ContentListActivityModule::class, ContentSearchModule::class],
		dependencies = [AppModuleSubComponent::class])
interface ContentSearchComponent : AppModuleInjector<ContentListActivity> {
	@Component.Builder
	abstract class Builder : AppModuleInjector.Builder<ContentListActivity>()
}

@ContentSearchScope
@Component(
		modules = [ContentSearchFragmentModule::class, ContentSearchModule::class],
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
class ContentSearchModule {
	@Provides
	@IntoMap
	@ViewModelKey(ContentSearchViewModel::class)
	internal fun provideContentSearchViewModel(): ViewModel = ContentSearchViewModel()

	@Provides
	internal fun createContentSearchViewModel(target: ContentListActivity, factory: ViewModelFactory) =
			factory.create<ContentSearchViewModel>(target)
}

@Module
class ContentSearchFragmentModule {
	@Provides
	internal fun provideContentListActivity(fragment: ContentListFragment) =
			fragment.activity as ContentListActivity
}
