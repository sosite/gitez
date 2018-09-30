package com.socros.android.app.gitez.contentdetails.data.remote

import com.socros.android.app.gitez.contentdetails.data.UserDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ContentDetailsApi {

	companion object {
		private const val PARAM_USERNAME = "username"
		private const val USER_DETAILS = "users/{$PARAM_USERNAME}"
	}

	@GET(USER_DETAILS)
	fun fetchUser(@Path(PARAM_USERNAME) username: String): Single<UserDetails>

}
