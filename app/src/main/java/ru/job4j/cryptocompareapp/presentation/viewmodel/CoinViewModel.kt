package ru.job4j.cryptocompareapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.job4j.cryptocompareapp.repository.AppRepository
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application, private val repository: AppRepository) :
    AndroidViewModel(application) {
    private val disposeBag = CompositeDisposable()
    private val liveDataCoinInfoList: MutableLiveData<List<Coin>> = MutableLiveData()
    private val liveDataSelectedCoin: MutableLiveData<Coin> = MutableLiveData()

    init {
        loadData()
        updateData()
    }

    private fun getCoinPriceInfoFromRepository(): Single<List<Coin>> {
        return repository.getCoinPriceInfoFromNet()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadData() {
        disposeBag.add(getCoinPriceInfoFromRepository()
            .retry()
            .subscribe(
                {
                    liveDataCoinInfoList.value = it
                    Log.d("TEST_OF_LOADING_DATA" + it.size, it.toString())
                },
                {
                    Log.d("TEST_OF_LOADING_DATA", it.message.toString())
                }
            ))
    }

    private fun updateData() {
        disposeBag.add(getCoinPriceInfoFromRepository()
            .delaySubscription(70, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribe(
                { it ->
                    liveDataCoinInfoList.value = it
                    if (getLiveDataSelectedCoin().value != null) {
                        val liveDataCoinId = getLiveDataSelectedCoin().value?.id
                        for (coin in it) {
                            if (coin.id == liveDataCoinId) {
                                setLiveDataSelectedCoin(coin)
                            }
                        }
                    }
                    Log.d("TEST_OF_LOADING_DATA" + it.size, it.toString())
                },
                {
                    Log.d("TEST_OF_LOADING_DATA", it.message.toString())
                }
            ))
    }

    fun getLiveDataCoinInfoList(): LiveData<List<Coin>> {
        return liveDataCoinInfoList
    }

    fun setLiveDataSelectedCoin(coin: Coin) {
        liveDataSelectedCoin.value = coin
    }

    fun getLiveDataSelectedCoin(): LiveData<Coin> {
        return liveDataSelectedCoin
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
    }
}
