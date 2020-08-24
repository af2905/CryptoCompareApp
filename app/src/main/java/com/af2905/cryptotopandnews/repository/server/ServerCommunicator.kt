package com.af2905.cryptotopandnews.repository.server

import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.entity.News
import io.reactivex.Single

class ServerCommunicator(private val apiService: ApiService) {
    fun getCoinPriceInfo(): Single<List<Coin>> {
        return apiService.getTopCoinsInfo()
            .map {
                if (!it.coins.isNullOrEmpty())
                    for (coin in it.coins) {
                        coin.id = coin.coinBasicInfo.coinBasicId.toInt()
                    }
                return@map it.coins
            }
    }

    fun getLatestNewsArticles(): Single<List<News>> {
        return apiService.getLatestNewsArticles()
            .map { return@map it.news }
    }
}
