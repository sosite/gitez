package com.socros.android.app.gitez.contentdetails.data

import com.socros.android.app.gitez.contentdetails.data.local.ContentDetailsDao
import com.socros.android.app.gitez.contentdetails.data.remote.ContentDetailsApi
import com.socros.android.lib.repository.NetworkBoundResource
import io.reactivex.Single
import javax.inject.Inject

class UserDetailsResource constructor(
		private val username: String,
		private val contentDetailsDao: ContentDetailsDao,
		private val contentDetailsApi: ContentDetailsApi)
	: NetworkBoundResource<UserDetails, UserDetails>() {

	override fun loadFromDb() = contentDetailsDao.getUser(username)

	// fetch data every time
	override fun shouldFetch(data: UserDetails?) = true

	override fun createCall(): Single<UserDetails> = contentDetailsApi.fetchUser(username)

	override fun saveCallResult(result: UserDetails) {
		contentDetailsDao.insertUser(result)
	}

	internal class Factory @Inject constructor(
			private val contentDetailsDao: ContentDetailsDao,
			private val contentDetailsApi: ContentDetailsApi) {

		fun createResource(username: String) =
				UserDetailsResource(username, contentDetailsDao, contentDetailsApi)
	}
}
