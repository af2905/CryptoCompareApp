package com.af2905.cryptotopandnews.di.component

import com.af2905.cryptotopandnews.di.module.ViewModelModule
import com.af2905.cryptotopandnews.di.scope.ViewModelScope
import com.af2905.cryptotopandnews.presentation.view.activity.MainActivity
import com.af2905.cryptotopandnews.presentation.view.fragment.DetailCoinFragment
import com.af2905.cryptotopandnews.presentation.view.fragment.NewsArticlesFragment
import com.af2905.cryptotopandnews.presentation.view.fragment.TopCoinsFragment
import dagger.Component

@ViewModelScope
@Component(modules = [ViewModelModule::class], dependencies = [RepositoryComponent::class])
interface ViewModelComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: TopCoinsFragment)
    fun inject(fragment: DetailCoinFragment)
    fun inject(fragment: NewsArticlesFragment)
}
