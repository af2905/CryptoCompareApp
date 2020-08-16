package ru.job4j.cryptocompareapp.di.component

import dagger.Component
import ru.job4j.cryptocompareapp.di.module.DatabaseModule
import ru.job4j.cryptocompareapp.repository.database.AppDatabase

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val appDatabase: AppDatabase
}
