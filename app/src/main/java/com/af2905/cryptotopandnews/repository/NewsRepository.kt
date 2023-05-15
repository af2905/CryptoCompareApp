package com.af2905.cryptotopandnews.repository

import com.af2905.cryptotopandnews.repository.api.NewsApi
import com.af2905.cryptotopandnews.repository.database.dao.NewsDao
import com.af2905.cryptotopandnews.repository.database.entity.News
import javax.inject.Inject

interface NewsRepository {
    suspend fun getLatestNewsArticles(): List<News>
}

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {
    override suspend fun getLatestNewsArticles(): List<News> {
        newsDao.insertNewsList(newsApi.getLatestNewsArticles().news)
        return newsDao.getNewsList()
    }
}