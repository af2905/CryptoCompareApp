package ru.job4j.cryptocompareapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.job4j.cryptocompareapp.repository.database.dao.CoinDao
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

@Database(entities = [Coin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}
