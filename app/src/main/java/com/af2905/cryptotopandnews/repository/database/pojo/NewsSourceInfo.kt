package com.af2905.cryptotopandnews.repository.database.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsSourceInfo(
    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("img")
    @Expose
    val img: String? = null
)
