package com.socros.android.app.gitez.contentdetails.di

import androidx.lifecycle.ViewModel
import com.socros.android.app.gitez.contentdetails.data.ContentDetailsRepository
import com.socros.android.app.gitez.contentdetails.view.UserDetailsActivity
import com.socros.android.app.gitez.contentdetails.view.UserDetailsViewModel
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelFactory
import com.socros.android.lib.androidcore.di.viewmodel.ViewModelKey
import com.socros.android.lib.androidcore.di.viewmodel.create
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ContentDetailsRepositoryModule::class])
class UserDetailsViewModelModule {
	@Provides
	@IntoMap
	@ViewModelKey(UserDetailsViewModel::class)
	internal fun provideUserDetailsViewModel(contentDetailsRepository: ContentDetailsRepository): ViewModel =
			UserDetailsViewModel(contentDetailsRepository)

	@Provides
	internal fun createUserDetailsViewModel(target: UserDetailsActivity, factory: ViewModelFactory)
			: UserDetailsViewModel = factory.create(target)
}
