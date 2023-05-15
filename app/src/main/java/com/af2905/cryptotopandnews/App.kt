package com.af2905.cryptotopandnews

import com.af2905.cryptotopandnews.di.component.*
import com.af2905.cryptotopandnews.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .context(this)
            .appModule(AppModule(applicationContext))
            .build()
    }
}
