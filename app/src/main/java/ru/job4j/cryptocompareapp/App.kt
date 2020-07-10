package ru.job4j.cryptocompareapp

import android.app.Application
import androidx.room.Room
import ru.job4j.cryptocompareapp.repository.database.AppDatabase

class App : Application() {
    private var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        initRoom()
    }

    private fun initRoom() {
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }
}