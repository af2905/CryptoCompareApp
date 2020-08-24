package com.af2905.cryptotopandnews.repository.database.pojo

import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinsListOfData(
    @SerializedName("Data")
    @Expose
    val coins: List<Coin>? = null
)
