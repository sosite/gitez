package com.socros.android.app.gitez.contentsearch.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.lib.androidcore.view.ACFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.content_list_fragment.currentValueTxt
import javax.inject.Inject

class ContentListFragment : ACFragment() {

	override val layoutResId = R.layout.content_list_fragment

	@Inject
	lateinit var searchViewModel: ContentSearchViewModel
	private lateinit var disposable: Disposable

	override fun onAttach(context: Context) {
		DaggerContentListFragmentComponent.builder().inject(this)
		super.onAttach(context)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		disposable = searchViewModel.searchResults.subscribe {
			currentValueTxt.text = it
		}
	}

	override fun onDestroyView() {
		disposable.dispose()
		super.onDestroyView()
	}

}
