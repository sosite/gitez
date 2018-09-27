package com.socros.android.app.gitez.contentsearch.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.socros.android.app.gitez.base.view.DataStatus
import com.socros.android.app.gitez.base.view.DataStatus.Success
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.UserItem
import com.socros.android.app.gitez.contentsearch.di.ContentSearchScope
import com.socros.android.app.gitez.contentsearch.di.DaggerContentListFragmentComponent
import com.socros.android.lib.androidcore.view.ACFragment
import com.socros.android.lib.util.visible
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.content_list_fragment.currentValueTxt
import kotlinx.android.synthetic.main.content_list_fragment.emptyPlaceholderTxt
import kotlinx.android.synthetic.main.content_list_fragment.errorBtn
import kotlinx.android.synthetic.main.content_list_fragment.errorContainer
import kotlinx.android.synthetic.main.content_list_fragment.errorDetailsTxt
import kotlinx.android.synthetic.main.content_list_fragment.errorHeaderTxt
import org.jetbrains.anko.textResource
import org.jetbrains.anko.toast
import javax.inject.Inject

@ContentSearchScope
class ContentListFragment : ACFragment() {

	override val layoutResId = R.layout.content_list_fragment

	@Inject
	lateinit var searchViewModel: ContentSearchViewModel
	private val disposable = CompositeDisposable()

	override fun onAttach(context: Context) {
		DaggerContentListFragmentComponent.builder().inject(this)
		super.onAttach(context)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		bindToSearchResults()
		bindToSearchStatus()
		errorBtn.setOnClickListener { searchViewModel.refreshResults() }
	}

	override fun onDestroyView() {
		disposable.dispose()
		super.onDestroyView()
	}

	private fun bindToSearchResults() {
		searchViewModel.searchResults.subscribe { itemsList ->
			var data = ""
			itemsList.forEach {
				data += "#${it.id}, "
				data += when (it) {
					is UserItem -> "User " + it.login
					is RepositoryItem -> "Repo " + it.fullName
					else -> "Id " + it.id
				}
				data += '\n'
			}
			currentValueTxt.text = data

		}.addTo(disposable)
	}

	private fun bindToSearchStatus() {
		searchViewModel.searchResultsStatus.subscribe {
			emptyPlaceholderTxt.visible =
					it is Success
					&& !it.hasData
					&& !searchViewModel.searchQuery.isNullOrBlank()

			errorContainer.visible =
					it is DataStatus.Error
					&& !it.hasData

			if (it is DataStatus.Error) {
				if (it.hasData) context?.toast(it.detailsStringRes)
				else {
					errorHeaderTxt.textResource = it.headerStringRes
					errorDetailsTxt.textResource = it.detailsStringRes
					errorBtn.textResource = it.buttonStringRes
				}
			}

		}.addTo(disposable)
	}

}
