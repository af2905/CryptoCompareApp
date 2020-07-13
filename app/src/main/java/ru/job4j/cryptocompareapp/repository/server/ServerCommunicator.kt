package ru.job4j.cryptocompareapp.repository.server

import com.google.gson.Gson
import io.reactivex.Flowable
import ru.job4j.cryptocompareapp.repository.database.entity.CoinPriceInfo
import ru.job4j.cryptocompareapp.repository.database.pojo.CoinPriceInfoRawData

class ServerCommunicator(private val apiService: ApiService) {
    fun getCoinPriceInfo(): Flowable<List<CoinPriceInfo>> {
        return apiService.getTopCoinsInfo(limit = 20)
            .map { it -> it.data?.map { it.coinInfo.name }?.joinToString(",") }
            .flatMap { apiService.getFullPriceList(fSyms = it) }
            .map { getCoinPriceListFromRawData(it) }
    }

    private fun getCoinPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
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
}


