package com.af2905.cryptotopandnews.repository.database.pojo

import com.af2905.cryptotopandnews.repository.database.entity.News
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsListOfData(
    @SerializedName("Data")
    @Expose
    val news: List<News>
)
