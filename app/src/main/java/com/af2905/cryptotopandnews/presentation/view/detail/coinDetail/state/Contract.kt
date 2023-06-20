package com.af2905.cryptotopandnews.presentation.view.detail.coinDetail.state

import com.af2905.cryptotopandnews.presentation.view.topCoins.item.CoinItem

class Contract {
    sealed interface CoinDetailState {
        object Loading : CoinDetailState
        data class Content(val coin: CoinItem) : CoinDetailState
        data class Error(val e: Exception) : CoinDetailState
    }
}