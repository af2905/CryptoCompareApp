package ru.job4j.cryptocompareapp.repository.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.job4j.cryptocompareapp.repository.database.pojo.CoinBasicInfo
import ru.job4j.cryptocompareapp.repository.database.pojo.DisplayCoinPriceInfo
import ru.job4j.cryptocompareapp.repository.database.pojo.RawCoinPriceInfo

@Entity(tableName = "datum")
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
