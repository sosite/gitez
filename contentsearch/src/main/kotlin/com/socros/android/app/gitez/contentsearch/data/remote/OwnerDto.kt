package com.socros.android.app.gitez.contentsearch.data.remote

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OwnerDto(
		@JsonProperty("user_id") val id: Long,
		@JsonProperty("display_name") val name: String,
		@JsonProperty("profile_image") val avatarUrl: String?
)
