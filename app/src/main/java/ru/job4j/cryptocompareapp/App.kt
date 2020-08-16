package ru.job4j.cryptocompareapp

import android.app.Application
import androidx.room.Room
import ru.job4j.cryptocompareapp.di.component.*
import ru.job4j.cryptocompareapp.di.module.ApiModule
import ru.job4j.cryptocompareapp.di.module.DatabaseModule
import ru.job4j.cryptocompareapp.di.module.RepositoryModule
import ru.job4j.cryptocompareapp.di.module.ViewModelModule
import ru.job4j.cryptocompareapp.repository.database.AppDatabase

class App : Application() {
    private lateinit var database: AppDatabase
    private lateinit var viewModelComponent: ViewModelComponent

    override fun onCreate() {
        super.onCreate()
        initRoom()
        initDagger()
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
        val apiComponent = DaggerApiComponent.builder()
            .apiModule(ApiModule())
            .build()

        val databaseComponent = DaggerDatabaseComponent.builder()
            .databaseModule(DatabaseModule(database))
            .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .apiComponent(apiComponent)
            .databaseComponent(databaseComponent)
            .repositoryModule(RepositoryModule())
            .build()

        this.viewModelComponent = DaggerViewModelComponent.builder()
            .repositoryComponent(repositoryComponent)
            .viewModelModule(ViewModelModule(this))
            .build()
    }
}
