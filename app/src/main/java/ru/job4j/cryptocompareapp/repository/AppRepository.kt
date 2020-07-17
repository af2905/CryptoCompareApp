package ru.job4j.cryptocompareapp.repository

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.database.entity.Datum
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

class AppRepository(
    private val serverCommunicator: ServerCommunicator,
    private val appDatabase: AppDatabase
) {
    fun getCoinPriceInfo(): Flowable<List<Datum>> {
        return serverCommunicator.getCoinPriceInfo()
            .map {
                Log.d("TAG", "toDB $it")
                appDatabase.datumDao().insertDatumList(it)
                Log.d("TAG",
                    "fromDB" + appDatabase.datumDao()
                        .getDatumList().size + " " + appDatabase.datumDao().getDatumList()
                        .toString()
                )
            }
            .flatMap {

                return@flatMap Flowable.just(appDatabase.datumDao().getDatumList())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}