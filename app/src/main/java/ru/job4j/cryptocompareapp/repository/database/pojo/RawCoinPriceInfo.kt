package ru.job4j.cryptocompareapp.repository.database.pojo

import androidx.room.Embedded
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RawCoinPriceInfo(
    @SerializedName("USD")
    @Expose
    @Embedded
    val coinPriceAndLastUpdate: CoinPriceAndLastUpdate? = null
)

