package ru.job4j.cryptocompareapp.repository

import io.reactivex.Single
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.entity.News
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

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
