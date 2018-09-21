package com.socros.android.lib.network.api

import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

abstract class ApiModule(private val baseUrl: String, private val debug: Boolean) {

	@Provides
	@Singleton
	internal fun provideRetrofit(): Retrofit {
		return Retrofit.Builder().apply {
			baseUrl(baseUrl)
			client(createClient())
			addConverterFactory(JacksonConverterFactory.create())
		}.build()
	}

	private fun createClient(): OkHttpClient {
		return OkHttpClient.Builder().apply {
			if (debug) {
				addInterceptor(HttpLoggingInterceptor())
			}
		}.build()
	}

}
