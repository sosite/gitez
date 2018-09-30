package com.socros.android.lib.androidcore.view.launcher

import android.content.Intent

interface Launcher {
	fun provideNextActivity(): Intent
}
