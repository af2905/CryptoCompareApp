package com.af2905.cryptotopandnews.di.component

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.af2905.cryptotopandnews.App
import com.af2905.cryptotopandnews.di.module.ViewModule
import com.af2905.cryptotopandnews.di.module.NetworkModule
import com.af2905.cryptotopandnews.di.module.RepositoryModule
import com.af2905.cryptotopandnews.di.module.StorageModule
import com.af2905.cryptotopandnews.di.scope.AppScope
import com.af2905.cryptotopandnews.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModule::class,
        StorageModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun getViewModelFactory(): ViewModelProvider.Factory
}