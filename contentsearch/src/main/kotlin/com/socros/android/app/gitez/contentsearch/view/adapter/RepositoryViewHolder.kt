package com.socros.android.app.gitez.contentsearch.view.adapter

import android.view.ViewGroup
import android.widget.TextView
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.R.string
import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.lib.androidcore.view.recycler.BaseViewHolder
import com.socros.android.lib.util.visible
import io.reactivex.Observer
import kotlinx.android.synthetic.main.content_list_repository_item.view.descriptionTxt
import kotlinx.android.synthetic.main.content_list_repository_item.view.languageTxt
import kotlinx.android.synthetic.main.content_list_repository_item.view.repoIdTxt
import kotlinx.android.synthetic.main.content_list_repository_item.view.repoNameTxt
import kotlinx.android.synthetic.main.content_list_repository_item.view.starCountTxt

class RepositoryViewHolder(parent: ViewGroup, itemClickObserver: Observer<in RepositoryItem>)
	: BaseViewHolder<RepositoryItem>(parent, itemClickObserver, R.layout.content_list_repository_item) {

	override fun updateView(item: RepositoryItem) {
		with(itemView) {
			repoNameTxt.text = item.fullName
			descriptionTxt.textOrHide = item.description
			repoIdTxt.text = res.getString(string.searchItem_idValue, item.id)
			languageTxt.textOrHide = item.language
			starCountTxt.text = item.starCount.toString()
		}
	}

	var TextView.textOrHide: String?
		get() = text.toString()
		set(value) {
			visible = !value.isNullOrBlank()
			text = value
		}

	internal class Factory: SearchItemViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup, itemClickObserver: Observer<SearchItem>)
				: BaseViewHolder<SearchItem> {

			return RepositoryViewHolder(parent, itemClickObserver) as BaseViewHolder<SearchItem>
		}
	}

}
