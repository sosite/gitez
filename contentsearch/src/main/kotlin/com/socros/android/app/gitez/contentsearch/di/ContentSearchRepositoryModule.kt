package com.socros.android.app.gitez.contentsearch.di

import android.content.Context
import com.socros.android.app.gitez.contentsearch.data.local.ContentSearchDatabase
import com.socros.android.lib.persistence.room.RoomModule
import com.socros.android.lib.persistence.room.buildDb
import dagger.Module
import dagger.Provides

@Module
class ContentSearchRepositoryModule : RoomModule {

	@ContentSearchScope
	@Provides
	internal fun provideDb(context: Context): ContentSearchDatabase = buildDb(context, "ContentSearch.db")

	@ContentSearchScope
	@Provides
	internal fun provideContentSearchDao(db: ContentSearchDatabase) = db.contentSearchDao()

}
