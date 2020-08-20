package ru.job4j.cryptocompareapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.job4j.cryptocompareapp.repository.database.dao.CoinDao
import ru.job4j.cryptocompareapp.repository.database.dao.NewsDao
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.entity.News

@Database(entities = [Coin::class, News::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun newsDao(): NewsDao
}
