package com.af2905.cryptotopandnews.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.af2905.cryptotopandnews.data.database.dto.News
import com.af2905.cryptotopandnews.data.database.dto.NewsSourceInfo

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val guId: String? = null,
    val publishedOn: Long? = null,
    val imageUrl: String? = null,
    val title: String? = null,
    val url: String? = null,
    val source: String? = null,
    val body: String? = null,
    val tags: String? = null,
    val categories: String? = null,
    val upVotes: String? = null,
    val downVotes: String? = null,
    val lang: String? = null,
    @Embedded
    val newsSourceInfo: NewsSourceInfo? = null
) {
    companion object {
        fun map(list: List<News>): List<NewsEntity> {
            return list.map { map(it) }
        }

        private fun map(news: News): NewsEntity {
            with(news) {
                return NewsEntity(
                    id = id,
                    guId = guId,
                    publishedOn = publishedOn,
                    imageUrl = imageUrl,
                    title = title,
                    url = url,
                    source = source,
                    body = body,
                    tags = tags,
                    categories = categories,
                    upVotes = upVotes,
                    downVotes = downVotes,
                    lang = lang,
                )
            }
        }
    }
}