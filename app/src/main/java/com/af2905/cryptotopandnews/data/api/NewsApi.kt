package com.af2905.cryptotopandnews.data.api

import com.af2905.cryptotopandnews.data.api.ApiParams.QUERY_PARAM_LANG
import com.af2905.cryptotopandnews.data.database.dto.NewsListOfData
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/news/")
    suspend fun getLatestNewsArticles(
        @Query(QUERY_PARAM_LANG) lang: String? = null
    ): NewsListOfData
}