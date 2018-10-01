package com.socros.android.app.gitez.contentsearch.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.socros.android.app.gitez.contentsearch.data.QuestionItem
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class ContentListAdapter(
		private val observable: Observable<List<QuestionItem>>,
		private val viewHolderFactory: QuestionViewHolder.Factory)
	: RecyclerView.Adapter<QuestionViewHolder>() {

	private val itemClickSubject = PublishSubject.create<QuestionItem>()
	val itemClicks: Observable<out QuestionItem> = itemClickSubject

	private var data: List<QuestionItem> = emptyList()

	init {
		setHasStableIds(true)
	}

	fun subscribe(): Disposable = observable.subscribe {
		data = it
		notifyDataSetChanged()
	}

	override fun getItemCount() = data.size

	override fun getItemId(position: Int) = data[position].id

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
		return viewHolderFactory.createViewHolder(parent, itemClickSubject)
	}

	override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
		holder.bindView(data[position])
	}

}
