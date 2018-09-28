package com.socros.android.app.gitez.contentsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty

@Entity(tableName = "repositories")
data class RepositoryItem(
		@PrimaryKey override val id: Long,
		@JsonProperty("full_name") val fullName: String,
		val description: String?,
		val language: String?,
		@JsonProperty("stargazers_count") val starCount: Int
) : SearchItem
