package ru.job4j.cryptocompareapp.repository

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

class AppRepository(
    private val serverCommunicator: ServerCommunicator,
    private val appDatabase: AppDatabase
) {
    fun getCoinPriceInfoFromNet(): Flowable<List<Coin>> {
        return serverCommunicator.getCoinPriceInfo()
            .map {
                appDatabase.coinDao().insertCoinList(it)
                val coins = appDatabase.coinDao().getCoinList()
                for ((i, coin) in coins.withIndex()) {
                    coin.number = i + 1
                }
            }
            .flatMap {
                return@flatMap Flowable.just(appDatabase.coinDao().getCoinList())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}