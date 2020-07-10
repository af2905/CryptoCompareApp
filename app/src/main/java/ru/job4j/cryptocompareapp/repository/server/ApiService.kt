package ru.job4j.cryptocompareapp.repository.server

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.job4j.cryptocompareapp.repository.database.pojo.CoinInfoListOfData
import ru.job4j.cryptocompareapp.repository.database.pojo.CoinPriceInfoRawData

interface ApiService {
    //https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>

    //https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=USD
    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val CURRENCY = "USD"
        private const val API_KEY =
            "51408d7f017b024af8fd145fac420c81222cde9c847182d33bc8bc30c9064959"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
    }
}