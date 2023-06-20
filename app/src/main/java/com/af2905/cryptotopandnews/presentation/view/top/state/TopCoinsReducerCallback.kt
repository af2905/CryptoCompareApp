package com.af2905.cryptotopandnews.presentation.view.top.state

import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem

interface TopCoinsReducerCallback {
    suspend fun onLoadData()
    suspend fun onLoadDataSuccess(list: List<CoinItem>)
    suspend fun onLoadDataError(e: Exception)
    suspend fun onOpenDetailClicked(id: String)

    suspend fun openDetailScreen(id: String)
}