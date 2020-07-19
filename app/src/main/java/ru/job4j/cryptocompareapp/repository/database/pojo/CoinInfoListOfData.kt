package ru.job4j.cryptocompareapp.repository.database.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

data class CoinInfoListOfData (
    @SerializedName("Data")
    @Expose
    val coins: List<Coin>? = null
)