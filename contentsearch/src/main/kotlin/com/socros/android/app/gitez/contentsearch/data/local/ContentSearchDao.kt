package com.socros.android.app.gitez.contentsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.socros.android.app.gitez.contentsearch.data.RepositoryItem
import com.socros.android.app.gitez.contentsearch.data.SearchItem
import com.socros.android.app.gitez.contentsearch.data.UserItem
import io.reactivex.Single

@Dao
interface ContentSearchDao {
	@Query("SELECT * FROM users WHERE login LIKE '%' || :query || '%' ORDER BY id ASC")
	fun searchUsers(query: String): Single<List<UserItem>>

	@Query("SELECT * FROM users")
	fun searchUsers(): Single<List<UserItem>>

	@Query("SELECT * FROM repositories WHERE fullName LIKE '%' || :query || '%' ORDER BY id ASC")
	fun searchRepositories(query: String): Single<List<RepositoryItem>>

	@Query("SELECT * FROM repositories")
	fun searchRepositories(): Single<List<RepositoryItem>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertUserItem(user: UserItem)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertRepositoryItem(repository: RepositoryItem)

	@Transaction
	fun insertSearchItems(searchItemList: List<SearchItem>) {
		for (item in searchItemList) when (item) {
			is UserItem -> insertUserItem(item)
			is RepositoryItem -> insertRepositoryItem(item)
		}
	}

}
