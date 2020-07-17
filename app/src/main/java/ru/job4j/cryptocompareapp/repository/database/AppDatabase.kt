package ru.job4j.cryptocompareapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.job4j.cryptocompareapp.repository.database.dao.DatumDao
import ru.job4j.cryptocompareapp.repository.database.entity.Datum

@Database(entities = [Datum::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun datumDao(): DatumDao
}