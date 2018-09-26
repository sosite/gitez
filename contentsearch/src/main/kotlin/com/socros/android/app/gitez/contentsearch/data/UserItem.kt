package com.socros.android.app.gitez.contentsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserItem(
		@PrimaryKey override val id: Long,
		val login: String,
		val avatarURl: String?
) : SearchItem
