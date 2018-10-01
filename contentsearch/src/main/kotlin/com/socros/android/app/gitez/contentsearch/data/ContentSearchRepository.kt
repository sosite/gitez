package com.socros.android.app.gitez.contentsearch.data

import com.socros.android.app.gitez.contentsearch.di.ContentSearchScope
import com.socros.android.lib.repository.Resource
import dagger.Lazy
import io.reactivex.Observable
import javax.inject.Inject

@ContentSearchScope
class ContentSearchRepository @Inject constructor() {

	@Inject
	internal lateinit var contentSearchResourceProvider: Lazy<ContentSearchResource>

	fun searchQustions(query: String): Observable<Resource<List<QuestionItem>?>> {
		return contentSearchResourceProvider.get().run {
			updateQuery(query)
			result
		}
	}

}
