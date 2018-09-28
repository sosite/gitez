package com.socros.android.app.gitez.contentsearch.view.adapter

import android.view.ViewGroup
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.lib.androidcore.view.recycler.BaseViewHolder
import io.reactivex.Observer

interface SearchItemViewHolderFactory {
	fun createViewHolder(parent: ViewGroup, itemClickObserver: Observer<SearchItem>): BaseViewHolder<SearchItem>
}
