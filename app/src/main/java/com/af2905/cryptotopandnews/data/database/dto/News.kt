package com.af2905.cryptotopandnews.data.database.dto

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id")
    val id: String,

    @SerializedName("guid")
    val guId: String? = null,

    @SerializedName("published_on")
    val publishedOn: Long? = null,

    @SerializedName("imageurl")
    val imageUrl: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("source")
    val source: String? = null,

    @SerializedName("body")
    val body: String? = null,

    @SerializedName("tags")
    val tags: String? = null,

    @SerializedName("categories")
    val categories: String? = null,

    @SerializedName("upvotes")
    val upVotes: String? = null,

    @SerializedName("downvotes")
    val downVotes: String? = null,

    @SerializedName("lang")
    val lang: String? = null,

    @SerializedName("source_info")
    val newsSourceInfo: NewsSourceInfo? = null
)
