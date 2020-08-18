package ru.job4j.cryptocompareapp

import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.pojo.*

class CoinTestHelper {
    fun createListOfCoins(): List<Coin> {
        val coins = mutableListOf<Coin>()
        for (i in 1..50) {
            coins.add(
                Coin(
                    i, CoinBasicInfo("$i", "name$i", "fullName$i"),
                    RawCoinPriceInfo(CoinPriceAndLastUpdate(i.toDouble())),
                    DisplayCoinPriceInfo(CoinPriceInfo("${i.toDouble()}"))
                )
            )
        }
        return coins
    }
}
