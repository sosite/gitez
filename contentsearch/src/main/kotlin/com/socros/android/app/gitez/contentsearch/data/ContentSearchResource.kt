package com.socros.android.app.gitez.contentsearch.data

import com.socros.android.app.gitez.contentsearch.data.local.ContentSearchDao
import com.socros.android.lib.repository.NetworkBoundResource
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

class ContentSearchResource @Inject constructor(private val contentSearchDao: ContentSearchDao)
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
					val list = ArrayList<SearchItem>(userList).also { it.addAll(repoList) }
					// TODO zip sorted lists in a better way
					list.sortBy { it.id }
					list
				})
	}

	override fun shouldFetch(data: List<SearchItem>?): Boolean {
		// fetch data every time right now
		return true
	}

	override fun createCall(): Single<List<SearchItem>> {
		return Single.just(arrayListOf(
				UserItem(1, "Jan Testowy", null),
				UserItem(2, "Joanna Testowa", null),
				UserItem(3, "Kolejny Nijaki", null),
				RepositoryItem(2, "Repo Testowe 1", null, "Kotlin", 22),
				UserItem(25, "Pusty Testowy", null),
				RepositoryItem(4, "Repo Testowe 2", "Lorem ipsum", "Java", 0)
		))
				.delay(2, SECONDS)
				.map { it }
	}

	override fun saveCallResult(result: List<SearchItem>) {
		contentSearchDao.insertSearchItems(result)
	}

}
