package com.socros.android.app.gitez.contentsearch.di

import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.app.gitez.contentsearch.data.UserItem
import com.socros.android.app.gitez.contentsearch.view.ContentSearchViewModel
import com.socros.android.app.gitez.contentsearch.view.adapter.ContentListAdapter
import com.socros.android.app.gitez.contentsearch.view.adapter.RepositoryViewHolder
import com.socros.android.app.gitez.contentsearch.view.adapter.SearchItemViewHolderFactory
import com.socros.android.app.gitez.contentsearch.view.adapter.UserViewHolder
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
class ContentListAdapterModule {

	companion object {
		const val SEARCH_TYPE_USER = 0
		const val SEARCH_TYPE_REPOSITORY = 1
	}

	@Provides
	internal fun provideContentListAdapter(
			contentSearchViewModel: ContentSearchViewModel,
			itemTypeMap: Map<KClass<out SearchItem>, Int>,
			viewHolderFactoryMap: Map<Int, @JvmSuppressWildcards SearchItemViewHolderFactory>): ContentListAdapter {

		return ContentListAdapter(contentSearchViewModel.searchResults, itemTypeMap, viewHolderFactoryMap)
	}

	@Provides
	internal fun provideItemTypeMap() =
			mapOf(UserItem::class to SEARCH_TYPE_USER,
					RepositoryItem::class to SEARCH_TYPE_REPOSITORY)

	@Provides
	@IntoMap
	@IntKey(SEARCH_TYPE_USER)
	fun provideUserViewHolderFactory(): SearchItemViewHolderFactory {
		return UserViewHolder.Factory()
	}

	@Provides
	@IntoMap
	@IntKey(SEARCH_TYPE_REPOSITORY)
	fun provideRepositoryViewHolderFactory(): SearchItemViewHolderFactory {
		return RepositoryViewHolder.Factory()
	}

}