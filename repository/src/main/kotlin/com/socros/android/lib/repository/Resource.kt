package com.socros.android.lib.repository

import java.util.Objects

sealed class Resource<out T>(val data: T?) {
	class LoadingInProgress<out T> internal constructor(data: T?) : Resource<T>(data)
	class Success<out T> internal constructor(data: T?) : Resource<T>(data)
	class NetworkError<out T> internal constructor(data: T?) : Resource<T>(data)
	class ServerError<out T> internal constructor(data: T?) : Resource<T>(data)

	override fun equals(other: Any?): Boolean {
		return super.equals(other)
				|| other is Resource<*> && this::class == other::class && sameData(other)
	}

	override fun hashCode(): Int {
		return Objects.hash(this, data)
	}

	fun sameData(other: Resource<*>): Boolean =
			data == other.data
					|| data is List<*> && other.data is List<*>
					&& data.toTypedArray() contentEquals other.data.toTypedArray()

}
