package com.af2905.cryptotopandnews

import com.af2905.cryptotopandnews.di.component.*
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication(), AppComponentStore {

    private val appComponent: AppComponent = DaggerAppComponent.builder()
        .context(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    override fun getComponent(): AppComponent {
        return appComponent
    }
}
