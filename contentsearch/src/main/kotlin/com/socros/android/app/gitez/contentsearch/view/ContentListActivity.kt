package com.socros.android.app.gitez.contentsearch.view

import android.annotation.SuppressLint
import android.os.Bundle
import com.socros.android.app.gitez.base.view.BaseActivity
import com.socros.android.app.gitez.contentsearch.R
import kotlinx.android.synthetic.main.content_list_activity.toolbar

class ContentListActivity : BaseActivity() {

	override val layoutResId = R.layout.content_list_activity

	@SuppressLint("InlinedApi")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(toolbar)
	}

}
