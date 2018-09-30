package com.socros.android.app.gitez.contentdetails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.socros.android.app.gitez.contentdetails.data.UserDetails
import io.reactivex.Single

@Dao
interface ContentDetailsDao {
	@Query("SELECT * FROM detailedUsers WHERE login IS :username")
	fun getUser(username: String): Single<UserDetails>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertUser(user: UserDetails)
}
