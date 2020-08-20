package ru.job4j.cryptocompareapp.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

@Dao
interface CoinDao {
    @Query("SELECT * FROM coins ORDER BY coinPrice DESC LIMIT 50")
    fun getCoinList(): List<Coin>

    @Query("SELECT * FROM coins WHERE id =:id")
    fun getCoinById(id: Int): Coin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinList(coinList: List<Coin>)
}
