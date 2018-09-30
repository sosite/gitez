package com.socros.android.app.gitez.contentsearch.view

import androidx.lifecycle.ViewModel
import com.socros.android.app.gitez.base.view.DataStatus
import com.socros.android.app.gitez.base.view.DataStatus.Error.ServerError
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.data.ContentSearchRepository
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.lib.repository.Resource
import com.socros.android.lib.util.composeAsync
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ContentSearchViewModel @Inject constructor(private val contentSearchRepository: ContentSearchRepository)
	: ViewModel() {

	private val searchQuerySubject = BehaviorSubject.create<String>()
	val searchQuery: String? get() = searchQuerySubject.value

	private val searchResultsSubject = BehaviorSubject.create<List<SearchItem>>()
	val searchResults: Observable<List<SearchItem>> = searchResultsSubject

	private val searchResultsStatusSubject = BehaviorSubject.create<DataStatus>()
	val searchResultsStatus: Observable<DataStatus> = searchResultsStatusSubject

	private var disposable: Disposable? = null

	fun updateSearchQuery(query: String, restoredValue: Boolean) {
		if (!restoredValue || !searchQuerySubject.hasValue()) {
			// if new query or need to initialize
			searchQuerySubject.onNext(query)
			refreshResults()
		}
	}

	fun refreshResults() {
		disposable?.dispose()
		disposable = contentSearchRepository.searchContent(searchQuery ?: "")
				.composeAsync()
				.subscribe { result ->
					searchResultsSubject.onNext(result.data!!)

					val hasData = result.data?.isNotEmpty() == true
					searchResultsStatusSubject.onNext(
							if (result is Resource.ServerError) {
								ServerError(hasData, detailsStringRes = R.string.contentSearch_serverError_details)

							} else DataStatus.fromRepositoryResource(result, hasData)
					)
				}
	}

	override fun onCleared() {
		disposable?.dispose()
	}

}
