package com.af2905.cryptotopandnews

import android.app.Application
import androidx.room.Room
import com.af2905.cryptotopandnews.di.component.*
import com.af2905.cryptotopandnews.di.module.ApiModule
import com.af2905.cryptotopandnews.di.module.DatabaseModule
import com.af2905.cryptotopandnews.di.module.RepositoryModule
import com.af2905.cryptotopandnews.di.module.ViewModelModule
import com.af2905.cryptotopandnews.repository.database.AppDatabase

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
