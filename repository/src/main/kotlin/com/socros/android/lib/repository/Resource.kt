package com.socros.android.lib.repository

sealed class Resource<T>(val data: T) {
	class LoadingInProgress<T> internal constructor(data: T?) : Resource<T?>(data)
	class Success<T> internal constructor(data: T) : Resource<T>(data)
	class NetworkError<T> internal constructor(data: T?) : Resource<T?>(data)
	class ServerError<T> internal constructor(data: T?) : Resource<T?>(data)
}
