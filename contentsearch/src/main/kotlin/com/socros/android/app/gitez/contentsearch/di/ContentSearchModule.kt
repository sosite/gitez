package com.socros.android.app.gitez.contentsearch.di

import androidx.lifecycle.ViewModel
import com.socros.android.app.gitez.contentsearch.data.ContentSearchRepository
import com.socros.android.app.gitez.contentsearch.view.ContentListActivity
import com.socros.android.app.gitez.contentsearch.view.ContentSearchViewModel
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelFactory
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelKey
import com.socros.android.lib.androidcore.di.viewmodel.create
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ContentSearchRepositoryModule::class])
class ContentSearchModule {
	@Provides
	@IntoMap
	@ViewModelKey(ContentSearchViewModel::class)
	internal fun provideContentSearchViewModel(contentSearchRepository: ContentSearchRepository): ViewModel =
			ContentSearchViewModel(contentSearchRepository)

	@Provides
	internal fun createContentSearchViewModel(target: ContentListActivity, factory: ViewModelFactory) =
			factory.create<ContentSearchViewModel>(target)
}
