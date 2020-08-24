package com.af2905.cryptotopandnews.repository.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.af2905.cryptotopandnews.repository.database.pojo.CoinBasicInfo
import com.af2905.cryptotopandnews.repository.database.pojo.DisplayCoinPriceInfo
import com.af2905.cryptotopandnews.repository.database.pojo.RawCoinPriceInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey
    var id: Int,

    @SerializedName("CoinInfo")
    @Expose
    @Embedded
    val coinBasicInfo: CoinBasicInfo,

    @SerializedName("RAW")
    @Expose
    @Embedded
    val rawCoinPriceInfo: RawCoinPriceInfo,

    @SerializedName("DISPLAY")
    @Expose
    @Embedded
    val displayCoinPriceInfo: DisplayCoinPriceInfo
)
