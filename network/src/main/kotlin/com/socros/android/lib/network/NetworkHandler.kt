package com.socros.android.lib.network

import android.content.Context
import org.jetbrains.anko.connectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
	@Suppress("DEPRECATION")
	val connected: Boolean
		get() = context.connectivityManager.activeNetworkInfo?.isConnectedOrConnecting
				?: false
}
