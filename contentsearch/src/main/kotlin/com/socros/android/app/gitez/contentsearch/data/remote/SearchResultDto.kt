package com.socros.android.app.gitez.contentsearch.data.remote

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchResultDto(
		val items: List<QuestionDto>
)
