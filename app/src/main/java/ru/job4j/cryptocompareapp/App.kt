package ru.job4j.cryptocompareapp

import android.app.Application
import androidx.room.Room
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.repository.database.AppDatabase

class App : Application() {
    private lateinit var database: AppDatabase
    private lateinit var viewModelComponent: ViewModelComponent

    override fun onCreate() {
        super.onCreate()
        initRoom()
    }

    private fun initRoom() {
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    fun getViewModelComponent(): ViewModelComponent {
        return viewModelComponent
    }

    private fun initDagger() {

    }
}