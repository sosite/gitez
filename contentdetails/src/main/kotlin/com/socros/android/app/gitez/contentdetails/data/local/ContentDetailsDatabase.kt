package com.socros.android.app.gitez.contentdetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.socros.android.app.gitez.contentdetails.data.UserDetails

@Database(entities = [UserDetails::class], version = 1)
abstract class ContentDetailsDatabase : RoomDatabase() {
	abstract fun contentDetailsDao(): ContentDetailsDao
}
