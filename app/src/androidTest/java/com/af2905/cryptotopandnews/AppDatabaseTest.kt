package com.af2905.cryptotopandnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.af2905.cryptotopandnews.repository.database.AppDatabase
import com.af2905.cryptotopandnews.repository.database.dao.CoinDao
import com.af2905.cryptotopandnews.repository.database.dao.NewsDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    @Rule
    lateinit var instantTaskExecutorRule: InstantTaskExecutorRule
    private lateinit var database: AppDatabase
    private lateinit var coinDao: CoinDao
    private lateinit var newsDao: NewsDao

    @Before
    fun initRoom() {
        instantTaskExecutorRule = InstantTaskExecutorRule()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        coinDao = database.coinDao()
        newsDao = database.newsDao()
    }

    @After
    fun afterTests() {
        database.close()
    }

    @Test
    fun checkLoadingAndReceivingData() {
        val coins = AndroidTestHelper().coins
        val twoCoinsFromList = listOf(coins[0], coins[1])
        coinDao.insertCoinList(twoCoinsFromList)
        val dbCoins = coinDao.getCoinList()
        assertEquals(2, dbCoins.size)

        val newsArticles = AndroidTestHelper().newsArticles
        val twoNewsFromList = listOf(newsArticles[0], newsArticles[1])
        newsDao.insertNewsList(twoNewsFromList)
        val dbNews = newsDao.getNewsList()
        assertEquals(2, dbNews.size)
    }

    @Test
    fun checkReceivingDataById() {
        val coins = AndroidTestHelper().coins
        coinDao.insertCoinList(coins)
        val dbCoins = coinDao.getCoinList()
        val firstCoinId = dbCoins[0].id
        val coin = coinDao.getCoinById(firstCoinId)
        assertEquals(dbCoins[0], coin)

        val newsArticles = AndroidTestHelper().newsArticles
        newsDao.insertNewsList(newsArticles)
        val dbNews = newsDao.getNewsList()
        val secondNewsId = dbNews[1].id
        val news = newsDao.getNewsById(secondNewsId.toInt())
        assertEquals(dbNews[1], news)
    }
}
