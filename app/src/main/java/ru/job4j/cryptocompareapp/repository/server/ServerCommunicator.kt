package ru.job4j.cryptocompareapp.repository.server

import io.reactivex.Single
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.entity.News

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
