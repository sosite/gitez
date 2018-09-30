package com.socros.android.lib.persistence.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

interface RoomModule

inline fun <reified Db : RoomDatabase> RoomModule.buildDb(
		context: Context,
		dbName: String,
		noinline apply: (RoomDatabase.Builder<Db>.() -> RoomDatabase.Builder<Db>)? = null)
		: Db =
		Room.databaseBuilder(context, Db::class.java, dbName)
				.let { apply?.invoke(it) ?: it }
				.build()
