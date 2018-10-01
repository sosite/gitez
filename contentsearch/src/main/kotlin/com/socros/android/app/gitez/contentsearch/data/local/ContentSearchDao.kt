package com.socros.android.app.gitez.contentsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.socros.android.app.gitez.contentsearch.data.QuestionItem
import io.reactivex.Single

@Dao
interface ContentSearchDao {
	@Query("SELECT * FROM questions WHERE title LIKE '%' || :query || '%' ORDER BY id ASC")
	fun searchQuestions(query: String): Single<List<QuestionItem>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertQuestions(questionList: List<QuestionItem>)

}
