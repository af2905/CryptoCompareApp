package com.af2905.cryptotopandnews.di.component

import android.content.Context

interface AppComponentStore {
    fun getComponent(): AppComponent
}

object AppComponentProvider {
    fun getAppComponent(context: Context): AppComponent {
        return (context.applicationContext as AppComponentStore).getComponent()
    }
}