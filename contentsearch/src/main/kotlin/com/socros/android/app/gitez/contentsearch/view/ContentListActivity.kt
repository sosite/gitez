package com.socros.android.app.gitez.contentsearch.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.socros.android.app.gitez.base.view.BaseActivity
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.lib.util.visible
import kotlinx.android.synthetic.main.content_list_activity.content
import kotlinx.android.synthetic.main.content_list_activity.placeholderGroup
import kotlinx.android.synthetic.main.content_list_activity.searchBtn
import kotlinx.android.synthetic.main.content_list_activity.toolbar

class ContentListActivity : BaseActivity(), OnActionExpandListener {

	override val layoutResId = R.layout.content_list_activity

	private lateinit var searchMenu: MenuItem
	private lateinit var searchView: SearchView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(toolbar)
		initView()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.contentsearch, menu)
		searchMenu = menu.findItem(R.id.actionSearch)
		initSearchView()
		return super.onCreateOptionsMenu(menu)
	}

	private fun initView() {
		// enable fade out animations
		content.layoutTransition = LayoutTransition().apply {
			disableTransitionType(LayoutTransition.CHANGE_APPEARING)
			disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
			disableTransitionType(LayoutTransition.APPEARING)
		}

		searchBtn.setOnClickListener { searchMenu.expandActionView() }
	}

	private fun initSearchView() {
		searchMenu.setOnActionExpandListener(this)
		searchView = searchMenu.actionView as SearchView

		searchView.apply {
			setOnQueryTextFocusChangeListener { _, hasFocus ->
				if (!hasFocus && searchView.query.isEmpty()) {
					searchMenu.collapseActionView()
				}
			}

			setOnQueryTextListener(object : OnQueryTextListener {
				override fun onQueryTextSubmit(query: String): Boolean {
					// unfocus search view
					content.requestFocus()
					return false
				}

				override fun onQueryTextChange(newText: String): Boolean {
					return true
				}
			})
		}
	}

	override fun onMenuItemActionExpand(item: MenuItem): Boolean {
		placeholderGroup.visible = false
		return true
	}

	override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
		placeholderGroup.visible = true
		return true
	}

}
