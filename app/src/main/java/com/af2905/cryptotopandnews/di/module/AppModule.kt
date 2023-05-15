package com.af2905.cryptotopandnews.di.module

import android.content.Context
import com.af2905.cryptotopandnews.di.qualifier.AppContext
import com.af2905.cryptotopandnews.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @AppScope
    @AppContext
    @Provides
    fun provideAppContext() = context
}