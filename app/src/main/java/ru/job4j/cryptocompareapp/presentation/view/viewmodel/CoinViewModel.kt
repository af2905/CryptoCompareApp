package ru.job4j.cryptocompareapp.presentation.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import ru.job4j.cryptocompareapp.repository.AppRepository

class CoinViewModel(application: Application, private val repository: AppRepository) :
    AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val liveDataTopCoinsInfo: MutableLiveData<String> = MutableLiveData()

    fun getTopCoinsInfo(): Unit {
        val disposable = repository.getTopCoinsInfo().subscribe { it ->
            liveDataTopCoinsInfo.value = it
            Log.d("TEST_OF_LOADING_DATA", it.toString())
        }
        compositeDisposable.add(disposable)
    }

    fun getLiveDataTopCoinsInfo(): LiveData<String> {
        return liveDataTopCoinsInfo
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}