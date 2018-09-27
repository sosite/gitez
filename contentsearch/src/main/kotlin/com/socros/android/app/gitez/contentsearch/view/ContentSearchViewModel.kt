package com.socros.android.app.gitez.contentsearch.view

import androidx.lifecycle.ViewModel
import com.socros.android.app.gitez.base.view.DataStatus
import com.socros.android.app.gitez.contentsearch.data.ContentSearchRepository
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.lib.util.composeAsync
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ContentSearchViewModel @Inject constructor(private val contentSearchRepository: ContentSearchRepository)
	: ViewModel() {

	private val searchResultsSubject = BehaviorSubject.create<List<SearchItem>>()
	private val searchResultsStatusSubject = BehaviorSubject.create<DataStatus>()
	val searchResults: Observable<List<SearchItem>> = searchResultsSubject
	val searchResultsStatus: Observable<DataStatus> = searchResultsStatusSubject

	var i = 0

	private var disposable: Disposable? = null

	fun updateSearchQuery(query: String) {
		disposable?.dispose()
		disposable = contentSearchRepository.searchContent(query)
				.composeAsync()
				.subscribe { result ->
					result.data?.let { searchResultsSubject.onNext(it) }
					searchResultsStatusSubject.onNext(DataStatus.fromRepositoryResource(result))
				}
	}

}
