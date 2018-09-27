package com.socros.android.app.gitez.contentsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty

@Entity(tableName = "users")
data class UserItem(
		@PrimaryKey override val id: Long,
		val login: String,
		@JsonProperty("avatar_url") val avatarURl: String?
) : SearchItem
