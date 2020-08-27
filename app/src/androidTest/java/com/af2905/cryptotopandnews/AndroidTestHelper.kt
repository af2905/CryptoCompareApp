package com.af2905.cryptotopandnews

import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.entity.News
import com.af2905.cryptotopandnews.repository.database.pojo.CoinBasicInfo
import com.af2905.cryptotopandnews.repository.database.pojo.CoinPriceAndLastUpdate
import com.af2905.cryptotopandnews.repository.database.pojo.CoinPriceInfo
import com.af2905.cryptotopandnews.repository.database.pojo.DisplayCoinPriceInfo
import com.af2905.cryptotopandnews.repository.database.pojo.RawCoinPriceInfo

class AndroidTestHelper {
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

    val newsList = listOf(News("1", "1"), News("2", "2"), News("3", "3"))
}
