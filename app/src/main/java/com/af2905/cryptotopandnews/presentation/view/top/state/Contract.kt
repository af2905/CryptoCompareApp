package com.af2905.cryptotopandnews.presentation.view.top.state

import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem

class Contract {
    sealed interface TopCoinsState {
        object Loading : TopCoinsState
        data class Content(val list: List<CoinItem>) : TopCoinsState
        data class Error(val e: Exception) : TopCoinsState
    }
}