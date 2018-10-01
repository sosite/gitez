package com.socros.android.app.gitez.contentsearch.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.socros.android.app.gitez.base.view.BaseActivity
import com.socros.android.app.gitez.base.view.DataStatus.InProgress
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.di.DaggerContentSearchActivityComponent
import com.socros.android.app.gitez.contentsearch.view.ContentSearchViewModel.InSwipeRefreshProgress
import com.socros.android.lib.util.addFragment
import com.socros.android.lib.util.visible
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.content_list_activity.placeholderGroup
import kotlinx.android.synthetic.main.content_list_activity.progressContainer
import kotlinx.android.synthetic.main.content_list_activity.searchBtn
import kotlinx.android.synthetic.main.content_list_activity.searchResultFragmentContainer
import kotlinx.android.synthetic.main.content_list_activity.toolbar
import javax.inject.Inject

class ContentListActivity : BaseActivity(), OnActionExpandListener {

	companion object {
		private const val STATE_SEARCH_QUERY = "stateSearchQuery"
	}

	override val layoutResId = R.layout.content_list_activity

	@Inject
	lateinit var searchViewModel: ContentSearchViewModel

	@Inject
	lateinit var contentListFragmentProvider: Lazy<ContentListFragment>

	private val disposable = CompositeDisposable()

	private lateinit var searchMenu: MenuItem
	private lateinit var searchView: SearchView

	// not null when search bar is active
	private var searchQuery: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		DaggerContentSearchActivityComponent.builder().inject(this)
		super.onCreate(savedInstanceState)
		setSupportActionBar(toolbar)
		initView()
		bindToSearchStatus()
		addFragment(R.id.searchResultFragmentContainer, contentListFragmentProvider)

		savedInstanceState?.let {
			searchQuery = it.getString(STATE_SEARCH_QUERY)
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		searchQuery?.let { outState.putString(STATE_SEARCH_QUERY, it) }
		super.onSaveInstanceState(outState)
	}

	override fun onDestroy() {
		disposable.dispose()
		super.onDestroy()
	}

	override fun onPause() {
		if (!searchQuery.isNullOrBlank()) searchView.clearFocus()
		super.onPause()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.contentsearch, menu)
		searchMenu = menu.findItem(R.id.actionSearch)
		initSearchView()
		return super.onCreateOptionsMenu(menu)
	}

	private fun initView() {
		searchBtn.setOnClickListener { searchMenu.expandActionView() }
	}

	private fun bindToSearchStatus() {
		searchViewModel.searchResultsStatus.subscribe {
			progressContainer.visible = it is InProgress && it !is InSwipeRefreshProgress
		}.addTo(disposable)
	}

	private fun initSearchView() {
		searchMenu.setOnActionExpandListener(this)
		searchView = searchMenu.actionView as SearchView

		searchQuery?.let { query ->
			searchMenu.expandActionView()
			searchView.setQuery(query, false)
			searchViewModel.updateSearchQuery(query, true)
			if (query.isNotBlank()) searchView.clearFocus()
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
					searchViewModel.updateSearchQuery(query, false)
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
		searchResultFragmentContainer.visible = visible
		placeholderGroup.visible = !visible
	}

}
