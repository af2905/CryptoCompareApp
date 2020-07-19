package ru.job4j.cryptocompareapp.repository

import android.util.Log
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
                Log.d("TAG", "toDB $it")
                appDatabase.coinDao().insertCoinList(it)
                Log.d(
                    "TAG",
                    "fromDB" + appDatabase.coinDao()
                        .getCoinList().size + " " + appDatabase.coinDao().getCoinList()
                        .toString()
                )
            }
            .flatMap {
                return@flatMap Flowable.just(appDatabase.coinDao().getCoinList())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

/*    fun getCoinPriceInfoFromDb(): Single<List<Coin>> {
        return Single.just(appDatabase.coinDao().getCoinList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }*/
}