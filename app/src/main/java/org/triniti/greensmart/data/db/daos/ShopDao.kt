package org.triniti.greensmart.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.triniti.greensmart.data.db.entities.Shop

@Dao
interface ShopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShops(bins: List<Shop>)

    @Query("select * from shop")
    fun getShops(): LiveData<List<Shop>>
}