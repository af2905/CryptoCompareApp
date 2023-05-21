package com.af2905.cryptotopandnews.presentation.view.top.di

import com.af2905.cryptotopandnews.presentation.view.top.TopCoinsViewModel
import com.af2905.cryptotopandnews.data.ToplistsRepository
import dagger.Module
import dagger.Provides

@Module
class TopCoinsModule {

    @Provides
    @TopCoinsScope
    fun provideViewModel(repository: ToplistsRepository): TopCoinsViewModel =
        TopCoinsViewModel(repository)
}