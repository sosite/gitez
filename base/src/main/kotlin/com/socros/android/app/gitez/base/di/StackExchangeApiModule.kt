package com.socros.android.app.gitez.base.di

import com.socros.android.app.gitez.base.BuildConfig
import com.socros.android.lib.network.api.ApiModule
import com.socros.android.lib.network.api.buildRetrofit
import dagger.Module
import dagger.Provides

@Module
class StackExchangeApiModule : ApiModule {

	@Provides
	internal fun provideRetrofit() = buildRetrofit(BuildConfig.URL_API, BuildConfig.DEBUG)

}
