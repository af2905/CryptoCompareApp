package com.af2905.cryptotopandnews.di.module

import com.af2905.cryptotopandnews.repository.NewsRepository
import com.af2905.cryptotopandnews.repository.NewsRepositoryImpl
import com.af2905.cryptotopandnews.repository.ToplistsRepository
import com.af2905.cryptotopandnews.repository.ToplistsRepositoryImpl

import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindsToplistRepository(toplistsRepository: ToplistsRepositoryImpl): ToplistsRepository

    @Binds
    fun bindsNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}