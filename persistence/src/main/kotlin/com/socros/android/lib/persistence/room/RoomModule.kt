package com.socros.android.lib.persistence.room

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Provides
import javax.inject.Singleton

abstract class RoomModule<Db : RoomDatabase>(private val dbClass: Class<Db>, private val dbName: String) {

	@Singleton
	@Provides
	internal fun provideDb(context: Application): Db {
		return Room.databaseBuilder(context.applicationContext, dbClass, dbName)
				.build()
	}

}
