package com.af2905.cryptotopandnews.repository.database.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceAndLastUpdate(
    @SerializedName("PRICE")
    @Expose
    val coinPrice: Double? = null,

    @SerializedName("LASTUPDATE")
    @Expose
    val coinLastUpdate: Int? = null
)
