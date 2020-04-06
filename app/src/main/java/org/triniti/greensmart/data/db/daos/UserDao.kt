package org.triniti.greensmart.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import org.triniti.greensmart.data.db.entities.CURRENT_USER_ID
import org.triniti.greensmart.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User): Long

    @Query("select * from user where uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>

    @Query("delete from user")
    suspend fun clearUserData()
}