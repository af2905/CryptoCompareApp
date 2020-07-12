package ru.job4j.cryptocompareapp.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.database.entity.CoinPriceInfo
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

class AppRepository(
    private val serverCommunicator: ServerCommunicator,
    private val appDatabase: AppDatabase
) {
    fun getCoinPriceInfo(): Single<List<CoinPriceInfo>> {
        return serverCommunicator.getCoinPriceInfo()
            .flatMap { if (appDatabase.coinPriceInfoDao().getPriceList().isEmpty()){
                appDatabase.coinPriceInfoDao().insertPriceList(it)
            }
                return@flatMap Single.just(appDatabase.coinPriceInfoDao().getPriceList())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}