package com.af2905.cryptotopandnews.data.api

import com.af2905.cryptotopandnews.data.api.ApiParams.DEFAULT_CURRENCY
import com.af2905.cryptotopandnews.data.api.ApiParams.DEFAULT_LIMIT
import com.af2905.cryptotopandnews.data.api.ApiParams.QUERY_PARAM_LIMIT
import com.af2905.cryptotopandnews.data.api.ApiParams.QUERY_PARAM_TO_SYMBOL
import com.af2905.cryptotopandnews.data.database.dto.CoinsListOfData
import retrofit2.http.GET
import retrofit2.http.Query

interface ToplistsApi {
    @GET("top/totalvolfull")
    suspend fun getToplistBy24HVolumeFullData(
        @Query(QUERY_PARAM_LIMIT) limit: Int = DEFAULT_LIMIT,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = DEFAULT_CURRENCY
    ): CoinsListOfData
}