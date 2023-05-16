package com.af2905.cryptotopandnews.presentation.view.news.di

import com.af2905.cryptotopandnews.presentation.view.news.NewsViewModel
import dagger.Component
import dagger.Module

@Component(modules = [NewsModule::class])
@NewsScope
interface NewsComponent {
    @Component.Builder
    interface Builder {
        fun build(): NewsComponent
    }

    fun getViewModel(): NewsViewModel
}