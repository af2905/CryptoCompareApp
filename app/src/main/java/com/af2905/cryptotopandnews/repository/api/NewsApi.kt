package com.af2905.cryptotopandnews.repository.api

import com.af2905.cryptotopandnews.repository.api.ApiParams.QUERY_PARAM_LANG
import com.af2905.cryptotopandnews.repository.database.dto.NewsListOfData
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/news/")
    suspend fun getLatestNewsArticles(
        @Query(QUERY_PARAM_LANG) lang: String? = null
    ): NewsListOfData
}