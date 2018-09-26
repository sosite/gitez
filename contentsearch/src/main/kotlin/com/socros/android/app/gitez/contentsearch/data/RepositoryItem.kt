package com.socros.android.app.gitez.contentsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryItem(
		@PrimaryKey override val id: Long,
		val fullName: String,
		val description: String?,
		val language: String,
		val starCount: Int
) : SearchItem
