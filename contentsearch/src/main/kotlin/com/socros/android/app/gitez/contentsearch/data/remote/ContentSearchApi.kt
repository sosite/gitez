package com.socros.android.app.gitez.contentsearch.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentSearchApi {

	@GET("search?site=stackoverflow")
	fun searchQuestions(@Query("intitle") query: String): Single<SearchResultDto>

}
