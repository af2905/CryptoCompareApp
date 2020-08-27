package com.af2905.cryptotopandnews

import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.entity.News
import com.af2905.cryptotopandnews.repository.database.pojo.CoinBasicInfo
import com.af2905.cryptotopandnews.repository.database.pojo.CoinPriceAndLastUpdate
import com.af2905.cryptotopandnews.repository.database.pojo.CoinPriceInfo
import com.af2905.cryptotopandnews.repository.database.pojo.DisplayCoinPriceInfo
import com.af2905.cryptotopandnews.repository.database.pojo.RawCoinPriceInfo
import io.reactivex.Single
import io.reactivex.observers.TestObserver

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

    fun <T> checkDataFlow(source: Single<List<T>>, list: List<T>, observer: TestObserver<List<T>>) {
        observer.assertNotSubscribed()
        source.subscribe(observer)
        observer.awaitTerminalEvent()
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.assertValues(list)
    }
}
