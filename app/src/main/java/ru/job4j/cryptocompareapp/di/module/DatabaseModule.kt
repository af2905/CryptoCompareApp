package ru.job4j.cryptocompareapp.di.module

import dagger.Module
import dagger.Provides
import ru.job4j.cryptocompareapp.repository.database.AppDatabase

@Module
class DatabaseModule(private val appDatabase: AppDatabase) {
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return appDatabase
    }
}
