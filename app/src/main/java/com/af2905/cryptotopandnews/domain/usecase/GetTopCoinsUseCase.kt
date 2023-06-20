package com.af2905.cryptotopandnews.domain.usecase

import com.af2905.cryptotopandnews.data.ToplistsRepository
import com.af2905.cryptotopandnews.domain.CoroutineUseCase
import com.af2905.cryptotopandnews.presentation.view.topCoins.item.CoinItem
import javax.inject.Inject

class GetTopCoinsUseCase @Inject constructor(
    private val repository: ToplistsRepository
) : CoroutineUseCase<TopCoinsParams, List<CoinItem>>() {
    override suspend fun execute(params: TopCoinsParams): List<CoinItem> {
        val result = repository.getTopCoins().filterNot { it.displayCoinPriceInfo == null }
        return CoinItem.map(result)
    }
}

object TopCoinsParams