package com.socros.android.app.gitez.contentsearch.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.lib.androidcore.view.recycler.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KClass

class ContentListAdapter(
		private val observable: Observable<List<SearchItem>>,
		private val itemTypeMap: Map<KClass<out SearchItem>, Int>,
		private val viewHolderFactoryMap: Map<Int, SearchItemViewHolderFactory>)
	: RecyclerView.Adapter<BaseViewHolder<SearchItem>>() {

	private val itemClickSubject = PublishSubject.create<SearchItem>()
	val itemClicks: Observable<out SearchItem> = itemClickSubject

	private var data: List<SearchItem> = emptyList()

	init {
		setHasStableIds(true)
	}

	fun subscribe(): Disposable = observable.subscribe {
		data = it
		notifyDataSetChanged()
	}

	override fun getItemCount() = data.size

	// we can't use plain item id because we mixed many data sources that they id's aren't unique
	override fun getItemId(position: Int) = data[position].let { it.id + it.hashCode() }

	override fun getItemViewType(position: Int) = itemTypeMap[data[position]::class]!!

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchItem> {
		return viewHolderFactoryMap[viewType]!!.createViewHolder(parent, itemClickSubject)
	}

	override fun onBindViewHolder(holder: BaseViewHolder<SearchItem>, position: Int) {
		holder.bindView(data[position])
	}

}
