package com.af2905.cryptotopandnews.repository.database.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinBasicInfo(
    @SerializedName("Id")
    val id: String,

    @SerializedName("Name")
    val name: String? = null,

    @SerializedName("FullName")
    val fullName: String? = null
)
