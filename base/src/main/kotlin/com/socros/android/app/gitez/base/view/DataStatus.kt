package com.socros.android.app.gitez.base.view

import com.socros.android.lib.repository.Resource

sealed class DataStatus {
	object InProgress : DataStatus()
	object Success : DataStatus()

	sealed class Error : DataStatus() {
		object NetworkError : Error()
		object ServerError : Error()
		abstract class FeatureError : Error()
	}

	companion object {
		fun fromRepositoryResource(resource: Resource<*>) = when (resource) {
			is Resource.LoadingInProgress -> InProgress
			is Resource.Success -> Success
			is Resource.NetworkError -> Error.NetworkError
			is Resource.ServerError -> Error.ServerError
		}
	}
}
