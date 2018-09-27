package com.socros.android.app.gitez.contentsearch.data.remote

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.socros.android.app.gitez.contentsearch.data.SearchItem

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchResultDto<T : SearchItem>(
		val items: List<T>
)
