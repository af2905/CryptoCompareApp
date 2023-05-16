package com.af2905.cryptotopandnews.di.module

import com.af2905.cryptotopandnews.di.scope.ActivityScope
import com.af2905.cryptotopandnews.presentation.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = []
    )
    abstract fun mainActivity(): MainActivity
}