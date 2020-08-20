package ru.job4j.cryptocompareapp.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.job4j.cryptocompareapp.repository.database.entity.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNewsList(): List<News>

    @Query("SELECT * FROM news WHERE id =:id")
    fun getNewsById(id: Int): News

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsList(newsList: List<News>)
}