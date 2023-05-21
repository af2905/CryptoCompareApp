package com.af2905.cryptotopandnews.di.module

import android.content.Context
import androidx.room.Room
import com.af2905.cryptotopandnews.di.scope.AppScope
import com.af2905.cryptotopandnews.data.database.AppDatabase
import com.af2905.cryptotopandnews.data.database.dao.CoinDao
import com.af2905.cryptotopandnews.data.database.dao.NewsDao
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @Provides
    fun providesNewsDao(database: AppDatabase): NewsDao {
        return database.newsDao()
    }

    @Provides
    fun providesCoinDao(database: AppDatabase): CoinDao {
        return database.coinDao()
    }

    companion object {
        @AppScope
        @Provides
        fun provideRoomDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
        }
    }
}
