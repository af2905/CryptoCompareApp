package com.af2905.cryptotopandnews.repository.database.pojo

import androidx.room.Embedded
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RawCoinPriceInfo(
    @SerializedName("USD")
    @Expose
    @Embedded
    val coinPriceAndLastUpdate: CoinPriceAndLastUpdate? = null
)
