package com.af2905.cryptotopandnews.di.module

import com.af2905.cryptotopandnews.data.NewsRepository
import com.af2905.cryptotopandnews.data.NewsRepositoryImpl
import com.af2905.cryptotopandnews.data.ToplistsRepository
import com.af2905.cryptotopandnews.data.ToplistsRepositoryImpl

import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindsToplistRepository(toplistsRepository: ToplistsRepositoryImpl): ToplistsRepository

    @Binds
    fun bindsNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}