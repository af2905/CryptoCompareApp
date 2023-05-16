package com.af2905.cryptotopandnews.repository.database.dto

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("CoinInfo")
    val coinBasicInfo: CoinBasicInfo,

    @SerializedName("RAW")
    val rawCoinPriceInfo: RawCoinPriceInfo,

    @SerializedName("DISPLAY")
    val displayCoinPriceInfo: DisplayCoinPriceInfo
)
