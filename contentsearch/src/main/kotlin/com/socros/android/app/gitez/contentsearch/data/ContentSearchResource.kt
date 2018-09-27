package com.socros.android.app.gitez.contentsearch.data

import com.socros.android.app.gitez.contentsearch.data.local.ContentSearchDao
import com.socros.android.app.gitez.contentsearch.data.remote.ContentSearchApi
import com.socros.android.lib.repository.NetworkBoundResource
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class ContentSearchResource @Inject constructor(
		private val contentSearchDao: ContentSearchDao,
		private val contentSearchApi: ContentSearchApi)
	: NetworkBoundResource<List<SearchItem>, List<SearchItem>>() {

	private lateinit var query: String

	internal fun updateQuery(query: String) {
		this.query = query
		reset()
	}

	override fun loadFromDb(): Single<List<SearchItem>> {
		return Single.zip(
				contentSearchDao.searchUsers(query),
				contentSearchDao.searchRepositories(query),
				BiFunction { userList, repoList ->
					ArrayList<SearchItem>(userList).apply {
						addAll(repoList)
						sortBy { it.id } // TODO zip sorted lists in a better way
					}
				})
	}

	override fun shouldFetch(data: List<SearchItem>?): Boolean {
		// fetch data every time right now
		return true
	}

	override fun createCall(): Single<List<SearchItem>> {
		val call: Single<List<SearchItem>> = Single.zip(
				contentSearchApi.searchUsers(query),
				contentSearchApi.searchRepositories(query),
				BiFunction { userListDto, repoListDto ->
					ArrayList<SearchItem>(userListDto.items).also { it.addAll(repoListDto.items) }
				}
		)
		// delay api request to bypass unauthenticated rate limit which for search query is 10 requests / minute
		// but calling 2 requests we have a 5 request / minute
		return call.delaySubscription(1500, MILLISECONDS)
	}

	override fun saveCallResult(result: List<SearchItem>) {
		contentSearchDao.insertSearchItems(result)
	}

}
