package com.socros.android.app.gitez.contentsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionItem(
		@PrimaryKey val id: Long,
		val title: String,
		val answerCount: Int,
		val userName: String,
		val userAvatarUrl: String?,
		val link: String
)
