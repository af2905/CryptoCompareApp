package com.af2905.cryptotopandnews.repository

import com.af2905.cryptotopandnews.repository.database.AppDatabase
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.entity.News
import com.af2905.cryptotopandnews.repository.server.ServerCommunicator
import io.reactivex.Single

class AppRepository(
    private val serverCommunicator: ServerCommunicator,
    private val appDatabase: AppDatabase
) {
    fun getCoinPriceInfoFromNet(): Single<List<Coin>> {
        return serverCommunicator.getCoinPriceInfo()
            .map { appDatabase.coinDao().insertCoinList(it) }
            .flatMap { return@flatMap Single.just(appDatabase.coinDao().getCoinList()) }
    }

    fun getLatestNewsArticlesFromNet(): Single<List<News>> {
        return serverCommunicator.getLatestNewsArticles()
            .map { appDatabase.newsDao().insertNewsList(it) }
            .flatMap { return@flatMap Single.just(appDatabase.newsDao().getNewsList()) }
    }
}
