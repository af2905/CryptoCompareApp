package com.af2905.cryptotopandnews.presentation.view.topCoins.callback

import com.af2905.cryptotopandnews.presentation.view.topCoins.item.CoinItem

interface TopCoinsActionCallback {
    suspend fun onLoadData()
    suspend fun onLoadDataSuccess(list: List<CoinItem>)
    suspend fun onLoadDataError(throwable: Throwable)
    suspend fun onOpenDetailClicked(id: String)
}

interface TopCoinsEffectCallback {
    suspend fun onOpenDetailScreen(id: String)
}