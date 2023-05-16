package com.af2905.cryptotopandnews.presentation.view.news.di

import com.af2905.cryptotopandnews.presentation.view.news.NewsViewModel
import dagger.Module
import dagger.Provides

@Module
class NewsModule {
    @Provides
    @NewsScope
    fun provideViewModel(): NewsViewModel = NewsViewModel()
}