package com.socros.android.app.gitez.contentsearch.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.socros.android.app.gitez.base.view.BaseActivity
import com.socros.android.app.gitez.contentsearch.R
import kotlinx.android.synthetic.main.content_list_activity.searchBtn
import kotlinx.android.synthetic.main.content_list_activity.toolbar

class ContentListActivity : BaseActivity() {

	override val layoutResId = R.layout.content_list_activity

	private lateinit var searchMenu: MenuItem

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(toolbar)
		initView()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.contentsearch, menu)
		searchMenu = menu.findItem(R.id.actionSearch)
		return super.onCreateOptionsMenu(menu)
	}

	private fun initView() {
		searchBtn.setOnClickListener { searchMenu.expandActionView() }
	}

}
