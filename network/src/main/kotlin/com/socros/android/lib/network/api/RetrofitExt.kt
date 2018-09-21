package com.socros.android.lib.network.api

import retrofit2.Retrofit

inline fun <reified Service>Retrofit.create() = create(Service::class.java)
