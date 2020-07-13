package ru.job4j.cryptocompareapp.repository

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.database.entity.CoinPriceInfo
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator
import java.util.concurrent.TimeUnit

class AppRepository(
    private val serverCommunicator: ServerCommunicator,
    private val appDatabase: AppDatabase
) {
    fun getCoinPriceInfo(): Flowable<List<CoinPriceInfo>> {
        return serverCommunicator.getCoinPriceInfo()
            .flatMap {
                if (appDatabase.coinPriceInfoDao().getPriceList().isEmpty()) {
                    appDatabase.coinPriceInfoDao().insertPriceList(it)
                }
                return@flatMap Flowable.just(appDatabase.coinPriceInfoDao().getPriceList())
            }
            .delaySubscription(5, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailInfo(fSym: String): Flowable<CoinPriceInfo> {
        return Flowable.just(appDatabase.coinPriceInfoDao().getPriceInfoAboutCoin(fSym))
    }
}