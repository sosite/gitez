package com.socros.android.app.gitez.contentdetails.data

import com.socros.android.app.gitez.contentdetails.di.ContentDetailsScope
import com.socros.android.lib.repository.Resource
import dagger.Lazy
import io.reactivex.Observable
import javax.inject.Inject

@ContentDetailsScope
class ContentDetailsRepository @Inject constructor() {

	@Inject
	internal lateinit var userDetailsResourceFactoryProvider: Lazy<UserDetailsResource.Factory>

	fun getUser(username: String): Observable<Resource<UserDetails?>> {
		return userDetailsResourceFactoryProvider.get().createResource(username).run {
			initialize()
			result
		}
	}

}
