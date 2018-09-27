package com.socros.android.app.gitez.base.view

import com.socros.android.app.gitez.base.R
import com.socros.android.lib.repository.Resource

sealed class DataStatus(val hasData: Boolean) {
	open class InProgress(hasData: Boolean) : DataStatus(hasData)
	class Success(hasData: Boolean) : DataStatus(hasData)

	sealed class Error(hasData: Boolean, val headerStringRes: Int, val detailsStringRes: Int, val buttonStringRes: Int)
		: DataStatus(hasData) {

		class NetworkError(
				hasData: Boolean,
				headerStringRes: Int = R.string.networkError_header,
				detailsStringRes: Int = R.string.networkError_details,
				buttonStringRes: Int = R.string.networkError_btn)
			: Error(hasData, headerStringRes, detailsStringRes, buttonStringRes)

		class ServerError(
				hasData: Boolean,
				headerStringRes: Int = R.string.serverError_header,
				detailsStringRes: Int = R.string.serverError_details,
				buttonStringRes: Int = R.string.serverError_btn)
			: Error(hasData, headerStringRes, detailsStringRes, buttonStringRes)

		abstract class FeatureError(hasData: Boolean, headerStringRes: Int, detailsStringRes: Int, buttonStringRes: Int)
			: Error(hasData, headerStringRes, detailsStringRes, buttonStringRes)
	}

	companion object {
		fun fromRepositoryResource(resource: Resource<*>, hasData: Boolean) = when (resource) {
			is Resource.LoadingInProgress -> InProgress(hasData)
			is Resource.Success -> Success(hasData)
			is Resource.NetworkError -> Error.NetworkError(hasData)
			is Resource.ServerError -> Error.ServerError(hasData)
		}
	}
}
