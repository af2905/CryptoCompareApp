package com.af2905.cryptotopandnews.repository.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.af2905.cryptotopandnews.repository.database.pojo.NewsSourceInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("guid")
    @Expose
    val guId: String? = null,

    @SerializedName("published_on")
    @Expose
    val publishedOn: Long? = null,

    @SerializedName("imageurl")
    @Expose
    val imageUrl: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("source")
    @Expose
    val source: String? = null,

    @SerializedName("body")
    @Expose
    val body: String? = null,

    @SerializedName("tags")
    @Expose
    val tags: String? = null,

    @SerializedName("categories")
    @Expose
    val categories: String? = null,

    @SerializedName("upvotes")
    @Expose
    val upVotes: String? = null,

    @SerializedName("downvotes")
    @Expose
    val downVotes: String? = null,

    @SerializedName("lang")
    @Expose
    val lang: String? = null,

    @SerializedName("source_info")
    @Expose
    @Embedded
    val newsSourceInfo: NewsSourceInfo? = null
)
