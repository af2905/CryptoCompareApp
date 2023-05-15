package com.af2905.cryptotopandnews.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.cryptotopandnews.repository.database.entity.Coin

@Dao
interface CoinDao {
    @Query("SELECT * FROM coins")
    fun getCoinList(): List<Coin>

    @Query("SELECT * FROM coins WHERE id =:id")
    fun getCoinById(id: Int): Coin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinList(coinList: List<Coin>)
}
