package com.socros.android.app.gitez.contentsearch.data.remote

import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.UserItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentSearchApi {

	@GET("search/users")
	fun searchUsers(@Query("q") query: String): Single<SearchResultDto<UserItem>>

	@GET("search/repositories")
	fun searchRepositories(@Query("q") query: String): Single<SearchResultDto<RepositoryItem>>

}
