package com.af2905.cryptotopandnews.repository

import com.af2905.cryptotopandnews.repository.api.NewsApi
import com.af2905.cryptotopandnews.repository.database.dao.NewsDao
import com.af2905.cryptotopandnews.repository.database.dto.News
import com.af2905.cryptotopandnews.repository.database.entity.NewsEntity
import javax.inject.Inject

interface NewsRepository {
    suspend fun getLatestNewsArticles(): List<NewsEntity>
    suspend fun getNewsById(id: String): NewsEntity
}

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {
    override suspend fun getLatestNewsArticles(): List<NewsEntity> {
        val list = newsApi.getLatestNewsArticles().news
        newsDao.save(NewsEntity.map(list))
        return newsDao.getAll()
    }

    override suspend fun getNewsById(id: String): NewsEntity {
        return newsDao.getNewsById(id)
    }
}