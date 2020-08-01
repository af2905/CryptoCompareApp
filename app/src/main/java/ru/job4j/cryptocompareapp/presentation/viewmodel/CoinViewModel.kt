package ru.job4j.cryptocompareapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
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

    private fun loadData() {
        val disposable = repository.getCoinPriceInfoFromNet()
            .retry()
            .subscribe({ it ->
                liveDataCoinInfoList.value = it
                Log.d("TEST_OF_LOADING_DATA" + it.size, it.toString())
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.message.toString())
            })
        disposeBag.add(disposable)
    }

    private fun updateData() {
        val disposable = repository.getCoinPriceInfoFromNet()
            .delaySubscription(1, TimeUnit.MINUTES)
            .repeat()
            .retry()
            .subscribe({ it ->
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
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.message.toString())
            })
        disposeBag.add(disposable)
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