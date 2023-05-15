package com.af2905.cryptotopandnews.di.module

import android.content.Context
import androidx.room.Room
import com.af2905.cryptotopandnews.di.qualifier.AppContext
import com.af2905.cryptotopandnews.di.scope.AppScope
import com.af2905.cryptotopandnews.repository.database.AppDatabase
import com.af2905.cryptotopandnews.repository.database.dao.CoinDao
import com.af2905.cryptotopandnews.repository.database.dao.NewsDao
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
        fun provideDoomDatabase(
            @AppContext context: Context
        ): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
        }
    }
}
