package ru.job4j.cryptocompareapp

import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.pojo.*

class CoinAndroidTestHelper {
    val coins = listOf(
        Coin(
            1, CoinBasicInfo("1", "name1", "fullName1"),
            RawCoinPriceInfo(CoinPriceAndLastUpdate(1.0)),
            DisplayCoinPriceInfo(CoinPriceInfo("1"))
        ),
        Coin(
            2, CoinBasicInfo("2", "name2", "fullName2"),
            RawCoinPriceInfo(CoinPriceAndLastUpdate(2.0)),
            DisplayCoinPriceInfo(CoinPriceInfo("2"))
        ),
        Coin(
            3, CoinBasicInfo("3", "name3", "fullName3"),
            RawCoinPriceInfo(CoinPriceAndLastUpdate(3.0)),
            DisplayCoinPriceInfo(CoinPriceInfo("3"))
        )
    )
}