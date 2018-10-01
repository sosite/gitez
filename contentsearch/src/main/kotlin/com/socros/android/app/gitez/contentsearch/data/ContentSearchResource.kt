package com.socros.android.app.gitez.contentsearch.data

import com.socros.android.app.gitez.contentsearch.data.local.ContentSearchDao
import com.socros.android.app.gitez.contentsearch.data.remote.ContentSearchApi
import com.socros.android.app.gitez.contentsearch.data.remote.QuestionDto
import com.socros.android.lib.repository.NetworkBoundResource
import io.reactivex.Single
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class ContentSearchResource @Inject constructor(
		private val contentSearchDao: ContentSearchDao,
		private val contentSearchApi: ContentSearchApi)
	: NetworkBoundResource<List<QuestionDto>, List<QuestionItem>>() {

	private lateinit var query: String

	internal fun updateQuery(query: String) {
		this.query = query
		reset()
	}

	override fun loadFromDb(): Single<List<QuestionItem>> {
		return if (query.isBlank()) Single.just(emptyList())
		else contentSearchDao.searchQuestions(query)
	}

	override fun shouldFetch(data: List<QuestionItem>?): Boolean {
		// fetch data every time when has a query to search
		// TODO check if we really need to sync data from API based on last sync time
		return query.isNotBlank()
	}

	override fun createCall(): Single<List<QuestionDto>> {
		val call: Single<List<QuestionDto>> = contentSearchApi.searchQuestions(query).map { it.items }
		// delay api request to bypass unauthenticated rate limit
		return call.delaySubscription(500, MILLISECONDS)
	}

	override fun saveCallResult(result: List<QuestionDto>) {
		contentSearchDao.insertQuestions(result.map { dto ->
			QuestionItem(
					dto.id,
					dto.title,
					dto.answerCount,
					dto.owner.name,
					dto.owner.avatarUrl,
					dto.link
			)
		})
	}

}
