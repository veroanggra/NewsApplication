package com.veroanggra.newsapplication.common.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.veroanggra.newsapplication.common.model.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(news: News): Long

    @Query("SELECT * FROM news")
    fun getAllNews(): LiveData<List<News>>

    @Delete
    suspend fun deleteNews(news: News)
}