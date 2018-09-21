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
import kotlinx.android.synthetic.main.content_list_activity.searchResultsFragment
import kotlinx.android.synthetic.main.content_list_activity.toolbar
import javax.inject.Inject

class ContentListActivity : BaseActivity(), OnActionExpandListener {

	companion object {
		private const val STATE_SEARCH_QUERY = "stateSearchQuery"
	}

	override val layoutResId = R.layout.content_list_activity

	private lateinit var searchMenu: MenuItem
	private lateinit var searchView: SearchView

	private var searchQuery: CharSequence? = null

	@Inject
	lateinit var searchViewModel: ContentSearchViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		DaggerContentSearchComponent.builder().inject(this)
		super.onCreate(savedInstanceState)
		setSupportActionBar(toolbar)

		savedInstanceState?.let {
			searchQuery = it.getCharSequence(STATE_SEARCH_QUERY)
		}

		initView()
	}

	override fun onSaveInstanceState(outState: Bundle) {
		searchQuery?.let { outState.putCharSequence(STATE_SEARCH_QUERY, it) }
		super.onSaveInstanceState(outState)
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

		if (searchQuery != null) {
			searchMenu.expandActionView()
			searchView.setQuery(searchQuery, false)
			searchView.clearFocus()
		}

		searchView.apply {
			setOnQueryTextFocusChangeListener { _, hasFocus ->
				if (!hasFocus && searchView.query.isEmpty()) {
					searchMenu.collapseActionView()
				}
			}

			setOnQueryTextListener(object : OnQueryTextListener {
				override fun onQueryTextSubmit(query: String): Boolean {
					// unfocus search view
					searchView.clearFocus()
					return false
				}

				override fun onQueryTextChange(query: String): Boolean {
					searchQuery = query
					searchViewModel.updateSearchQuery(query)
					return true
				}
			})
		}
	}

	override fun onMenuItemActionExpand(item: MenuItem): Boolean {
		onSearchViewVisibilityChanged(true)
		return true
	}

	override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
		searchQuery = null
		onSearchViewVisibilityChanged(false)
		return true
	}

	private fun onSearchViewVisibilityChanged(visible: Boolean) {
		searchResultsFragment.view?.visible = visible
		placeholderGroup.visible = !visible
	}

}
