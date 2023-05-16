package com.af2905.cryptotopandnews.presentation.view.top.di

import com.af2905.cryptotopandnews.di.component.AppComponent
import com.af2905.cryptotopandnews.presentation.view.top.TopCoinsViewModel
import dagger.Component

@Component(modules = [TopCoinsModule::class], dependencies = [AppComponent::class])
@TopCoinsScope
interface TopCoinsComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): TopCoinsComponent
    }

    fun getViewModel(): TopCoinsViewModel
}