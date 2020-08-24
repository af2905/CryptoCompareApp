package com.af2905.cryptotopandnews.di.component

import com.af2905.cryptotopandnews.di.module.DatabaseModule
import com.af2905.cryptotopandnews.repository.database.AppDatabase
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val appDatabase: AppDatabase
}
