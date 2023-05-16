package com.af2905.cryptotopandnews.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.cryptotopandnews.repository.database.entity.Coin

@Dao
interface CoinDao {
    @Query("SELECT * FROM coins")
    suspend fun getAll(): List<Coin>?

    @Query("SELECT * FROM coins WHERE id =:id")
    suspend fun getCoinById(id: Int): Coin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: List<Coin>)
}