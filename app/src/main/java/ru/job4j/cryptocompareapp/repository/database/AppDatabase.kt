package ru.job4j.cryptocompareapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.job4j.cryptocompareapp.repository.database.dao.CoinPriceInfoDao
import ru.job4j.cryptocompareapp.repository.database.entity.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}