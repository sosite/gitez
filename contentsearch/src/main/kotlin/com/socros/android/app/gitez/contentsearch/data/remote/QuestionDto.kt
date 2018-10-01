package com.socros.android.app.gitez.contentsearch.data.remote

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class QuestionDto(
		@JsonProperty("question_id") val id: Long,
		val title: String,
		@JsonProperty("answer_count") val answerCount: Int,
		@JsonProperty("owner") val owner: OwnerDto
)
