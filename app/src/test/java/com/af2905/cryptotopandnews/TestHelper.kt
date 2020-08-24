package com.af2905.cryptotopandnews

import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.entity.News
import com.af2905.cryptotopandnews.repository.database.pojo.*

class TestHelper {
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

    fun createListOfNews(): List<News> {
        val newsArticles = mutableListOf<News>()
        for (i in 1..50) {
            newsArticles.add(News("$i", "$i", i.toLong()))
        }
        return newsArticles
    }
}
