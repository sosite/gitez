package com.socros.android.app.gitez.contentsearch.view.adapter

import android.view.ViewGroup
import com.socros.android.app.gitez.base.glide.GlideApp
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.R.string
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.app.gitez.contentsearch.data.UserItem
import com.socros.android.lib.androidcore.view.recycler.BaseViewHolder
import io.reactivex.Observer
import kotlinx.android.synthetic.main.content_list_user_item.view.avatarImg
import kotlinx.android.synthetic.main.content_list_user_item.view.userIdTxt
import kotlinx.android.synthetic.main.content_list_user_item.view.userNameTxt

class UserViewHolder(parent: ViewGroup, itemClickObserver: Observer<in UserItem>)
	: BaseViewHolder<UserItem>(parent, itemClickObserver, R.layout.content_list_user_item) {

	override fun updateView(item: UserItem) {
		with(itemView) {
			GlideApp.with(context)
					.load(item.avatarURl)
					.placeholder(R.drawable.item_img_placeholder)
					.circleCrop()
					.into(avatarImg)

			userNameTxt.text = item.login
			userIdTxt.text = res.getString(string.searchItem_idValue, item.id)
		}
	}

	internal class Factory: SearchItemViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup, itemClickObserver: Observer<SearchItem>)
				: BaseViewHolder<SearchItem> {

			return UserViewHolder(parent, itemClickObserver) as BaseViewHolder<SearchItem>
		}
	}

}
