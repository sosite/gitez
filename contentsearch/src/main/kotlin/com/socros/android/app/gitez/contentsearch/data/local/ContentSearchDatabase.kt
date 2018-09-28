package com.socros.android.app.gitez.contentsearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.UserItem

@Database(entities = [RepositoryItem::class, UserItem::class], version = 1)
abstract class ContentSearchDatabase : RoomDatabase() {
	abstract fun contentSearchDao(): ContentSearchDao
}
