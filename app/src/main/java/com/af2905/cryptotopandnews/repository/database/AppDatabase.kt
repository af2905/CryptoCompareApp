package com.af2905.cryptotopandnews.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.af2905.cryptotopandnews.repository.database.dao.CoinDao
import com.af2905.cryptotopandnews.repository.database.dao.NewsDao
import com.af2905.cryptotopandnews.repository.database.entity.CoinEntity
import com.af2905.cryptotopandnews.repository.database.entity.NewsEntity

@Database(entities = [CoinEntity::class, NewsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun newsDao(): NewsDao
}
