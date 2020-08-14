package ru.job4j.cryptocompareapp

import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.pojo.*

class CoinTestClass {
    val coins = listOf(
        Coin(
            1, 1,
            CoinBasicInfo("1", "btc", "bitcoin"),
            RawCoinPriceInfo(CoinPriceAndLastUpdate(100.0)),
            DisplayCoinPriceInfo(CoinPriceInfo("100"))
        ),
        Coin(
            2, 2,
            CoinBasicInfo("2", "bch", "bitcoin cash"),
            RawCoinPriceInfo(CoinPriceAndLastUpdate(50.0)),
            DisplayCoinPriceInfo(CoinPriceInfo("50"))
        )
    )
}