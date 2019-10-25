package org.triniti.greensmart.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.triniti.greensmart.data.db.entities.Bin

@Dao
interface BinsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBins(bins: List<Bin>)

    @Query("select * from bin ")
    fun getBins(): LiveData<List<Bin>>
}