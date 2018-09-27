package com.socros.android.app.gitez.contentsearch.view

import android.content.Context
import android.os.Bundle
import android.view.View
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.UserItem
import com.socros.android.app.gitez.contentsearch.di.ContentSearchScope
import com.socros.android.app.gitez.contentsearch.di.DaggerContentListFragmentComponent
import com.socros.android.lib.androidcore.view.ACFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.content_list_fragment.currentValueTxt
import javax.inject.Inject

@ContentSearchScope
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
		disposable = searchViewModel.searchResults.subscribe { itemsList ->
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
		}
	}

	override fun onDestroyView() {
		disposable.dispose()
		super.onDestroyView()
	}

}
