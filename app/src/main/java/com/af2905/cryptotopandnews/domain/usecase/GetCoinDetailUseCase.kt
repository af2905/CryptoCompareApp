package com.af2905.cryptotopandnews.domain.usecase

import com.af2905.cryptotopandnews.data.ToplistsRepository
import com.af2905.cryptotopandnews.domain.CoroutineUseCase
import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: ToplistsRepository
) : CoroutineUseCase<CoinDetailParams, CoinItem>(){
    override suspend fun execute(params: CoinDetailParams): CoinItem {
        val result = repository.getCoinById(params.id)
        return CoinItem.map(result)
    }
}

data class CoinDetailParams(val id: String)