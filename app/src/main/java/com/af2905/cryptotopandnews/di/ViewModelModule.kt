package com.af2905.cryptotopandnews.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af2905.cryptotopandnews.domain.usecase.GetCoinDetailUseCase
import com.af2905.cryptotopandnews.domain.usecase.GetTopCoinsUseCase
import com.af2905.cryptotopandnews.presentation.view.detail.coinDetail.CoinDetailViewModel
import com.af2905.cryptotopandnews.presentation.view.news.NewsViewModel
import com.af2905.cryptotopandnews.presentation.view.top.TopCoinsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class ViewModelModule {

    @Provides
    @[IntoMap ViewModelKey(KEY_TOP_COINS)]
    fun provideTopCoinsViewModel(getTopCoinsUseCase: GetTopCoinsUseCase): ViewModel =
        TopCoinsViewModel(getTopCoinsUseCase)

    @Provides
    @[IntoMap ViewModelKey(KEY_COIN_DETAIL)]
    fun provideCoinDetailViewModel(getCoinDetailUseCase: GetCoinDetailUseCase): ViewModel =
        CoinDetailViewModel(getCoinDetailUseCase)

    @Provides
    @[IntoMap ViewModelKey(KEY_NEWS)]
    fun provideNewsViewModel(): ViewModel = NewsViewModel()

    @Provides
    fun viewModelFactory(creators: Map<String, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }

    companion object {
        const val KEY_TOP_COINS = "KEY_TOP_COINS"
        const val KEY_NEWS = "KEY_NEWS"
        const val KEY_COIN_DETAIL = "KEY_COIN_DETAIL"
        const val KEY_NEWS_DETAIL = "KEY_COIN_DETAIL"
    }
}