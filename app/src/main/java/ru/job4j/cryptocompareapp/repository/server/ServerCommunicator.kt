package ru.job4j.cryptocompareapp.repository.server

import android.util.Log
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ServerCommunicator(private val apiService: ApiService) {

    fun getTopCoinsInfo(): Single<String?> {
        return apiService.getTopCoinsInfo()
            .map { it -> it.data?.map { it.coinInfo.name }?.joinToString(",") }
            .compose(singleTransformer())
            .doOnError { t: Throwable? -> Log.d(SERVER_COMMUNICATOR_TAG, t?.message.toString()) }
    }

    private fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retry(DEFAULT_RETRY_ATTEMPTS)
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 10L
        private const val DEFAULT_RETRY_ATTEMPTS = 4L
        private const val SERVER_COMMUNICATOR_TAG = "ServerCommunicator"
    }
}


