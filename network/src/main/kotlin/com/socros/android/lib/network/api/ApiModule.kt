package com.socros.android.lib.network.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

interface ApiModule

fun ApiModule.buildRetrofit(
		baseUrl: String,
		debug: Boolean,
		callAdapterFactory: CallAdapter.Factory = RxJava2CallAdapterFactory.create()): Retrofit =
		Retrofit.Builder().apply {
			baseUrl(baseUrl)
			client(createClient(debug))
			addConverterFactory(createJacksonConverter())
			addCallAdapterFactory(callAdapterFactory)
		}.build()

private fun ApiModule.createClient(debug: Boolean): OkHttpClient {
	return OkHttpClient.Builder().apply {
		if (debug) {
			addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
		}
	}.build()
}

private fun ApiModule.createJacksonConverter(): Converter.Factory =
		JacksonConverterFactory.create(
				ObjectMapper().registerModule(KotlinModule()))
