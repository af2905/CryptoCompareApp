package com.af2905.cryptotopandnews.di.module

import com.af2905.cryptotopandnews.repository.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val appDatabase: AppDatabase) {
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return appDatabase
    }
}
