package com.socros.android.lib.androidcore.view.recycler

/**
 * @param <T> Item that will be received in #onBindViewHolder
 */
interface ViewHolderBinder<in T> {

	fun bindView(item: T)

}
