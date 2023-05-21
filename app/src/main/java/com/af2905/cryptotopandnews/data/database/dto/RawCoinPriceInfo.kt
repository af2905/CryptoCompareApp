package com.af2905.cryptotopandnews.data.database.dto

import androidx.room.Embedded
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RawCoinPriceInfo(
    @SerializedName("USD")
    @Expose
    @Embedded
    val coinPriceAndLastUpdate: CoinPriceAndLastUpdate? = null
)
