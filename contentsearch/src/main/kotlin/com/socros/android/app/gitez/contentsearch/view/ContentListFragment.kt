package com.socros.android.app.gitez.contentsearch.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import com.socros.android.app.gitez.base.view.DataStatus.Success
import com.socros.android.app.gitez.base.view.ErrorContainer
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.di.ContentSearchScope
import com.socros.android.app.gitez.contentsearch.di.DaggerContentListFragmentComponent
import com.socros.android.app.gitez.contentsearch.view.ContentSearchViewModel.InSwipeRefreshProgress
import com.socros.android.app.gitez.contentsearch.view.adapter.ContentListAdapter
import com.socros.android.lib.androidcore.view.ACFragment
import com.socros.android.lib.util.visible
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.content_list_fragment.emptyPlaceholderTxt
import kotlinx.android.synthetic.main.content_list_fragment.includedErrorContainer
import kotlinx.android.synthetic.main.content_list_fragment.recyclerView
import kotlinx.android.synthetic.main.content_list_fragment.swipeRefresh
import org.jetbrains.anko.toast
import javax.inject.Inject

@ContentSearchScope
class ContentListFragment : ACFragment() {

	override val layoutResId = R.layout.content_list_fragment
	private lateinit var errorContainer: ErrorContainer

	@Inject
	lateinit var searchViewModel: ContentSearchViewModel

	@Inject
	lateinit var adapter: ContentListAdapter

	private val disposable = CompositeDisposable()

	override fun onAttach(context: Context) {
		DaggerContentListFragmentComponent.builder().inject(this)
		super.onAttach(context)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		initSwipeRefresh()
		initRecyclerView()
		errorContainer = ErrorContainer(includedErrorContainer) { searchViewModel.refreshResults() }

		bindToSearchResults()
		bindToSearchStatus()
		bindToRecyclerClickListener()
	}

	override fun onDestroyView() {
		disposable.dispose()
		super.onDestroyView()
	}

	private fun initSwipeRefresh() {
		swipeRefresh.setOnRefreshListener {
			searchViewModel.refreshResults()
		}
	}

	private fun initRecyclerView() {
		recyclerView.let {
			it.layoutManager = LinearLayoutManager(context)
			it.adapter = adapter
		}
	}

	private fun bindToSearchResults() {
		adapter.subscribe().addTo(disposable)
	}

	private fun bindToSearchStatus() {
		searchViewModel.searchResultsStatus.subscribe {
			swipeRefresh.isRefreshing = it is InSwipeRefreshProgress

			emptyPlaceholderTxt.visible =
					it is Success
					&& !it.hasData
					&& !searchViewModel.searchQuery.isNullOrBlank()

			errorContainer.updateVisibility(it) { error -> context?.toast(error.detailsStringRes) }

		}.addTo(disposable)
	}

	private fun bindToRecyclerClickListener() {
		adapter.itemClicks.subscribe {
			context?.toast("${it.title.parseAsHtml()}\n#${it.id}")
		}.addTo(disposable)
	}

}
