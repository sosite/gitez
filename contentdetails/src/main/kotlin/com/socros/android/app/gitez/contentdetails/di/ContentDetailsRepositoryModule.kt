package com.socros.android.app.gitez.contentdetails.di

import android.content.Context
import com.socros.android.app.gitez.base.di.GithubApiModule
import com.socros.android.app.gitez.contentdetails.data.local.ContentDetailsDatabase
import com.socros.android.app.gitez.contentdetails.data.remote.ContentDetailsApi
import com.socros.android.lib.androidcore.di.AppContext
import com.socros.android.lib.network.api.create
import com.socros.android.lib.persistence.room.RoomModule
import com.socros.android.lib.persistence.room.buildDb
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [GithubApiModule::class])
class ContentDetailsRepositoryModule : RoomModule {

	@ContentDetailsScope
	@Provides
	internal fun provideDb(@AppContext context: Context): ContentDetailsDatabase =
			buildDb(context, "ContentDetails.db") { fallbackToDestructiveMigration() }

	@ContentDetailsScope
	@Provides
	internal fun provideContentDetailsDao(db: ContentDetailsDatabase) = db.contentDetailsDao()

	@ContentDetailsScope
	@Provides
	internal fun provideContentDetailsApi(retrofit: Retrofit): ContentDetailsApi = retrofit.create()

}
