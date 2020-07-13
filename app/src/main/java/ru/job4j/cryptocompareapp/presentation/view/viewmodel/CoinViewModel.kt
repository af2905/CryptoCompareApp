package ru.job4j.cryptocompareapp.presentation.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import ru.job4j.cryptocompareapp.repository.AppRepository
import ru.job4j.cryptocompareapp.repository.database.entity.CoinPriceInfo

class CoinViewModel(application: Application, private val repository: AppRepository) :
    AndroidViewModel(application) {
    private val disposeBag = CompositeDisposable()
    private val liveDataCoinPriceList: MutableLiveData<List<CoinPriceInfo>> = MutableLiveData()
    private val liveDataDetailInfo: MutableLiveData<CoinPriceInfo> = MutableLiveData()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String) {
        val disposable = repository.getDetailInfo(fSym).subscribe({ it ->
            liveDataDetailInfo.value = it
            Log.d("TEST_OF_LOADING_DETAIL", it.toString())
        }, {
            Log.d("TEST_OF_LOADING_DETAIL", it.message.toString())
        })
        disposeBag.add(disposable)
    }

    private fun loadData(): Unit {
        val disposable = repository.getCoinPriceInfo().subscribe({ it ->
            liveDataCoinPriceList.value = it
            Log.d("TEST_OF_LOADING_DATA", it.toString())
        }, {
            Log.d("TEST_OF_LOADING_DATA", it.message.toString())
        })
        disposeBag.add(disposable)
    }

    fun getLiveDataDetailInfo(): LiveData<CoinPriceInfo> {
        return liveDataDetailInfo
    }

    fun getLiveDataFullPriceList(): LiveData<List<CoinPriceInfo>> {
        return liveDataCoinPriceList
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
    }
}