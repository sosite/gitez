package com.socros.android.app.gitez.contentdetails.data

import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@Entity(tableName = "detailedUsers", primaryKeys = ["id", "login"])
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDetails(
		val id: Long,
		val login: String,
		val name: String?,
		@JsonProperty("avatar_url") val avatarURl: String?,
		val bio: String?,
		val blog: String?,
		val location: String?,
		val email: String?,
		val followers: Int,
		val following: Int
)
