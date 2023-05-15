package com.af2905.cryptotopandnews.di.component

import android.content.Context
import com.af2905.cryptotopandnews.App
import com.af2905.cryptotopandnews.di.ViewModule
import com.af2905.cryptotopandnews.di.module.AppModule
import com.af2905.cryptotopandnews.di.module.NetworkModule
import com.af2905.cryptotopandnews.di.module.RepositoryModule
import com.af2905.cryptotopandnews.di.module.StorageModule
import com.af2905.cryptotopandnews.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModule::class,
        StorageModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }
}