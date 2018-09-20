package com.socros.android.app.gitez.contentsearch.view

import android.os.Bundle
import android.view.Menu
import com.socros.android.app.gitez.base.view.BaseActivity
import com.socros.android.app.gitez.contentsearch.R
import kotlinx.android.synthetic.main.content_list_activity.toolbar

class ContentListActivity : BaseActivity() {

	override val layoutResId = R.layout.content_list_activity

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(toolbar)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.contentsearch, menu)
		return super.onCreateOptionsMenu(menu)
	}

}
