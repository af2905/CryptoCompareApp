package com.af2905.cryptotopandnews.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.cryptotopandnews.repository.database.entity.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedOn DESC LIMIT 50")
    suspend fun getNewsList(): List<News>

    @Query("SELECT * FROM news WHERE id =:id")
    suspend fun getNewsById(id: Int): News

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsList(newsList: List<News>)
}
