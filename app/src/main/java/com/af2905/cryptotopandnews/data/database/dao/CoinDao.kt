package com.af2905.cryptotopandnews.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.cryptotopandnews.data.database.entity.CoinEntity

private const val DEFAULT_LIMIT = 100

@Dao
interface CoinDao {
    @Query("SELECT * FROM coins LIMIT :limit")
    suspend fun getAll(limit: Int = DEFAULT_LIMIT): List<CoinEntity>?

    @Query("SELECT * FROM coins WHERE id =:id")
    suspend fun getCoinById(id: String): CoinEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: List<CoinEntity>)
}