package org.triniti.greensmart.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.triniti.greensmart.data.db.entities.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(news: List<News>)

    @Query("select * from news")
    fun getNews(): LiveData<List<News>>
}
