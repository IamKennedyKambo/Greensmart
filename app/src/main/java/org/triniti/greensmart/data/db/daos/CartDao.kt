package org.triniti.greensmart.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.triniti.greensmart.data.db.entities.Cart

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: Cart): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(products: List<Cart>)

    @Query("select * from cart")
    fun getCart(): LiveData<List<Cart>>

    @Query("delete from cart where id = :id")
    suspend fun delete(id: Int)

    @Query("delete from cart")
    suspend fun clear()
}