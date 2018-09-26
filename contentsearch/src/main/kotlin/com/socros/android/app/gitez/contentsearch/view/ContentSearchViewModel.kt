package com.socros.android.app.gitez.contentsearch.view

import androidx.lifecycle.ViewModel
import com.socros.android.app.gitez.contentsearch.data.ContentSearchRepository
import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.UserItem
import com.socros.android.lib.util.composeAsync
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ContentSearchViewModel @Inject constructor(private val contentSearchRepository: ContentSearchRepository)
	: ViewModel() {

	private val searchResultsSubject = BehaviorSubject.create<String>()
	val searchResults: Observable<String> = searchResultsSubject

	var i = 0

	private var disposable: Disposable? = null

	fun updateSearchQuery(query: String) {
		disposable?.dispose()
		disposable = contentSearchRepository.searchContent(query)
				.composeAsync()
				.subscribe {
					var data = ""
					it.data?.forEach {
						data += "#${it.id}, "
						data += when (it) {
							is UserItem -> "User " + it.login
							is RepositoryItem -> "Repo " + it.fullName
							else -> "Id " + it.id
						}
						data += '\n'
					}
					searchResultsSubject.onNext("" + ++i + "\n" + data)
				}
	}

}
