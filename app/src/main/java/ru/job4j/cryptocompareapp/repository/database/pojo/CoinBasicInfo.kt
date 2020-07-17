package ru.job4j.cryptocompareapp.repository.database.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinBasicInfo(
    @SerializedName("Id")
    @Expose
    val coinBasicId: String,

    @SerializedName("Name")
    @Expose
    val name: String? = null,

    @SerializedName("FullName")
    @Expose
    val fullName: String? = null
)