package com.socros.android.app.gitez.contentsearch.di

import android.content.Context
import com.socros.android.app.gitez.base.di.GithubApiModule
import com.socros.android.app.gitez.contentsearch.data.local.ContentSearchDatabase
import com.socros.android.app.gitez.contentsearch.data.remote.ContentSearchApi
import com.socros.android.lib.androidcore.di.AppContext
import com.socros.android.lib.network.api.create
import com.socros.android.lib.persistence.room.RoomModule
import com.socros.android.lib.persistence.room.buildDb
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [GithubApiModule::class])
class ContentSearchRepositoryModule : RoomModule {

	@ContentSearchScope
	@Provides
	internal fun provideDb(@AppContext context: Context): ContentSearchDatabase =
			buildDb(context, "ContentSearch.db") { fallbackToDestructiveMigration() }

	@ContentSearchScope
	@Provides
	internal fun provideContentSearchDao(db: ContentSearchDatabase) = db.contentSearchDao()

	@ContentSearchScope
	@Provides
	internal fun provideContentSearchApi(retrofit: Retrofit): ContentSearchApi = retrofit.create()

}
