package com.af2905.cryptotopandnews.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.entity.News
import java.util.concurrent.TimeUnit

class AppViewModel() : ViewModel()
    /*AndroidViewModel(application) {
    private val disposeBag = CompositeDisposable()
    private val liveDataCoinInfoList: MutableLiveData<List<Coin>> = MutableLiveData()
    private val liveDataSelectedCoin: MutableLiveData<Coin> = MutableLiveData()
    private val liveDataNewsArticlesList: MutableLiveData<List<News>> = MutableLiveData()
    private val liveDataErrorWhenLoadingData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadTopCoins()
        loadNewsArticles()
        updateTopCoins()
        updateNewsArticles()
    }

    private fun getCoinPriceInfoFromRepository(): Single<List<Coin>> {
        return repository.getCoinPriceInfoFromNet()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getLatestNewsArticlesFromRepository(): Single<List<News>> {
        return repository.getLatestNewsArticlesFromNet()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun loadTopCoins() {
        disposeBag.add(
            getCoinPriceInfoFromRepository()
                .doOnError { liveDataErrorWhenLoadingData.value = true }
                .retry()
                .subscribe(
                    {
                        liveDataErrorWhenLoadingData.value = false
                        liveDataCoinInfoList.value = it
                        Log.d("TEST_OF_LOADING_DATA" + it.size, it.toString())
                    },
                    {
                        Log.d("TEST_OF_LOADING_DATA", it.message.toString())
                    }
                )
        )
    }

    private fun updateTopCoins() {
        disposeBag.add(
            getCoinPriceInfoFromRepository()
                .delaySubscription(1, TimeUnit.MINUTES)
                .repeat()
                .retry()
                .subscribe(
                    {
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
                )
        )
    }

    private fun loadNewsArticles() {
        disposeBag.add(
            getLatestNewsArticlesFromRepository()
                .retry()
                .subscribe(
                    {
                        liveDataNewsArticlesList.value = it
                        Log.d("TEST_OF_LOADING_DATA" + it.size, it.toString())
                    },
                    {
                        Log.d("TEST_OF_LOADING_DATA", it.message.toString())
                    }
                )
        )
    }

    private fun updateNewsArticles() {
        disposeBag.add(
            getLatestNewsArticlesFromRepository()
                .delaySubscription(5, TimeUnit.MINUTES)
                .repeat()
                .retry()
                .subscribe(
                    { it ->
                        liveDataNewsArticlesList.value = it
                        Log.d("TEST_OF_LOADING_DATA" + it.size, it.toString())
                    },
                    {
                        Log.d("TEST_OF_LOADING_DATA", it.message.toString())
                    }
                )
        )
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

    fun getLiveDataNewsArticlesList(): LiveData<List<News>> {
        return liveDataNewsArticlesList
    }

    fun getLiveDataErrorWhenLoadingData(): LiveData<Boolean> {
        return liveDataErrorWhenLoadingData
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
    }*/
//}
