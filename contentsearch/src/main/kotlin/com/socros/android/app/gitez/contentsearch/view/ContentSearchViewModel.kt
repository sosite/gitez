package com.socros.android.app.gitez.contentsearch.view

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ContentSearchViewModel : ViewModel() {

	private val searchResultsSubject = BehaviorSubject.create<String>()
	val searchResults: Observable<String>
		get() = searchResultsSubject

	fun updateSearchQuery(query: String) {
		searchResultsSubject.onNext(query)
	}

}
