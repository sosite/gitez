package com.socros.android.app.gitez.contentsearch.view.adapter

import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import com.socros.android.app.gitez.base.glide.GlideApp
import com.socros.android.app.gitez.contentsearch.R
import com.socros.android.app.gitez.contentsearch.data.QuestionItem
import com.socros.android.lib.androidcore.view.recycler.BaseViewHolder
import io.reactivex.Observer
import kotlinx.android.synthetic.main.content_list_question_item.view.answersTxt
import kotlinx.android.synthetic.main.content_list_question_item.view.questionTitleTxt
import kotlinx.android.synthetic.main.content_list_question_item.view.userAvatarImg
import kotlinx.android.synthetic.main.content_list_question_item.view.userNameTxt

class QuestionViewHolder(parent: ViewGroup, itemClickObserver: Observer<QuestionItem>)
	: BaseViewHolder<QuestionItem>(parent, itemClickObserver, R.layout.content_list_question_item) {

	override fun updateView(item: QuestionItem) {
		with(itemView) {
			GlideApp.with(context)
					.load(item.userAvatarUrl)
					.placeholder(R.drawable.item_img_placeholder)
					.circleCrop()
					.into(userAvatarImg)

			questionTitleTxt.text = item.title.parseAsHtml()
			answersTxt.text = res.getString(R.string.searchItem_answersCountValue, item.answerCount)
			userNameTxt.text = item.userName.parseAsHtml()
		}
	}

	class Factory {
		fun createViewHolder(parent: ViewGroup, itemClickObserver: Observer<QuestionItem>) =
				QuestionViewHolder(parent, itemClickObserver)
	}

}
