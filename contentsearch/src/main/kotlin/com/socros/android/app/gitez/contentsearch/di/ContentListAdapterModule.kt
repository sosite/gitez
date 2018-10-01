package com.socros.android.app.gitez.contentsearch.di

import com.socros.android.app.gitez.contentsearch.view.ContentSearchViewModel
import com.socros.android.app.gitez.contentsearch.view.adapter.ContentListAdapter
import com.socros.android.app.gitez.contentsearch.view.adapter.QuestionViewHolder
import dagger.Module
import dagger.Provides

@Module
class ContentListAdapterModule {

	@Provides
	internal fun provideContentListAdapter(
			contentSearchViewModel: ContentSearchViewModel,
			viewHolderFactory: QuestionViewHolder.Factory): ContentListAdapter {

		return ContentListAdapter(contentSearchViewModel.searchResults, viewHolderFactory)
	}

	@Provides
	internal fun provideQuestionViewHolderFactory() = QuestionViewHolder.Factory()

}