package ru.job4j.cryptocompareapp.repository.server

import io.reactivex.Single
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

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
}
