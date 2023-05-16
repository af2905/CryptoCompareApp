package com.af2905.cryptotopandnews.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.cryptotopandnews.repository.database.dto.News
import com.af2905.cryptotopandnews.repository.database.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedOn DESC LIMIT 50")
    suspend fun getAll(): List<NewsEntity>

    @Query("SELECT * FROM news WHERE id =:id")
    suspend fun getNewsById(id: String): NewsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(newsList: List<NewsEntity>)
}
