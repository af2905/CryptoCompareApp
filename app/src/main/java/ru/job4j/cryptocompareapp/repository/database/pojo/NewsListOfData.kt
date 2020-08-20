package ru.job4j.cryptocompareapp.repository.database.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import ru.job4j.cryptocompareapp.repository.database.entity.News

data class NewsListOfData(
    @SerializedName("Data")
    @Expose
    val news: List<News>? = null
)