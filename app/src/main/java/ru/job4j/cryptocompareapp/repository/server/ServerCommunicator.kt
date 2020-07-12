package ru.job4j.cryptocompareapp.repository.server

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import ru.job4j.cryptocompareapp.repository.database.entity.CoinPriceInfo
import ru.job4j.cryptocompareapp.repository.database.pojo.CoinPriceInfoRawData
import java.util.concurrent.TimeUnit

class ServerCommunicator(private val apiService: ApiService) {
    fun getCoinPriceInfo(): Single<List<CoinPriceInfo>> {
        return apiService.getTopCoinsInfo(limit = 20)
            .map { it -> it.data?.map { it.coinInfo.name }?.joinToString(",") }
            .compose(singleTransformer())
            .doOnError { t -> Log.d(SERVER_COMMUNICATOR_TAG, t.message.toString()) }
            .flatMap { apiService.getFullPriceList(fSyms = it) }
            .map { getCoinPriceListFromRawData(it) }
    }

    private fun getCoinPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet ) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
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


