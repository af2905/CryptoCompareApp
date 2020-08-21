package ru.job4j.cryptocompareapp.repository.server

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.job4j.cryptocompareapp.repository.database.pojo.CoinsListOfData
import ru.job4j.cryptocompareapp.repository.database.pojo.NewsListOfData

interface ApiService {
    // https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit: Int = 100,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinsListOfData>

    // https://min-api.cryptocompare.com/data/v2/news/?lang=EN
    @GET("v2/news/")
    fun getLatestNewsArticles(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANG) lang: String = LANGUAGE
    ): Single<NewsListOfData>

    companion object {
        private const val API_KEY =
            "51408d7f017b024af8fd145fac420c81222cde9c847182d33bc8bc30c9064959"
        private const val QUERY_PARAM_API_KEY = "api_key"
        const val BASE_IMAGE_URL = "https://cryptocompare.com"

        // for getTopCoinsInfo
        private const val CURRENCY = "USD"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"

        // for getLatestNewsArticles
        private const val LANGUAGE = "EN"
        private const val QUERY_PARAM_LANG = "lang"
    }
}
