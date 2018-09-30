/*
* Copyright (C) 2014 Alejandro Rodriguez Salamanca.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.socros.android.lib.androidcore.view.recycler

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.detaches
import com.socros.android.lib.util.inflate
import io.reactivex.Observer

/**
 * @param <T> Item that will be received in #onBindViewHolder
 * @author original author - Alejandro Rodriguez (https://github.com/Alexrs95)
 * @author modified by - Wojciech Rozwadowski
 */
abstract class BaseViewHolder<T : Any>(parent: ViewGroup, itemClickObserver: Observer<in T>? = null, layoutResId: Int)
	: ViewHolder(parent.inflate(layoutResId)), ViewHolderBinder<T> {

	protected val context: Context = itemView.context
	protected val res: Resources = context.resources

	protected open val clickListenerContainer: View?
		get() = itemView

	protected lateinit var item: T

	init {
		initClickObserver(itemClickObserver)
	}

	private fun initClickObserver(itemClickObserver: Observer<in T>?) {
		fun View.subscribeClicks(clickObserver: Observer<in T>) {
			clicks()
					.takeUntil((parent as ViewGroup).detaches())
					.map { item }
					.subscribe(clickObserver)
		}

		itemClickObserver?.let { observer ->
			val clickContainer = clickListenerContainer
			if (clickContainer != null && clickContainer.parent is ViewGroup) {
				clickContainer.subscribeClicks(observer)

			} else {
				itemView.doOnPreDraw {
					// if item aren't accessible immediately
					clickListenerContainer?.subscribeClicks(observer)
				}
			}
		}
	}

	override fun bindView(item: T) {
		this.item = item
		updateView(item)
	}

	protected abstract fun updateView(item: T)

}
