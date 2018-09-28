package com.socros.android.app.gitez.contentsearch.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
interface SearchItem {
	val id: Long
}
