package com.socros.android.lib.androidcore.di

import com.socros.android.lib.androidcore.ACApp

interface AppProvider {
	fun provideApp(): ACApp
}
