package com.socros.android.app.gitez.base

import com.socros.android.lib.androidcore.ACApp
import com.socros.android.lib.tim.Tim

class GitezApp : ACApp() {

	init {
		Tim.init(BuildConfig.DEBUG)
	}

}
