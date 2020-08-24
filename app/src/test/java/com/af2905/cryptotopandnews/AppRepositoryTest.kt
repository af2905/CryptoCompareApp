package com.af2905.cryptotopandnews

import com.af2905.cryptotopandnews.repository.AppRepository
import com.af2905.cryptotopandnews.repository.database.AppDatabase
import com.af2905.cryptotopandnews.repository.server.ServerCommunicator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppRepositoryTest {
    @MockK
    private lateinit var serverCommunicator: ServerCommunicator

    @MockK
    private lateinit var appDatabase: AppDatabase

    @InjectMockKs
    private lateinit var appRepository: AppRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }

    @Test
    fun checkDataFlowFromServerCommunicator() {
        val coins = TestHelper().createListOfCoins()
        every { appDatabase.coinDao().getCoinList() } returns coins
        every { serverCommunicator.getCoinPriceInfo() } returns Single.just(
            appDatabase.coinDao().getCoinList()
        )

        val news = TestHelper().createListOfNews()
        every { appDatabase.newsDao().getNewsList() } returns news
        every { serverCommunicator.getLatestNewsArticles() } returns Single.just(
            appDatabase.newsDao().getNewsList()
        )

        with(TestHelper()) {
            checkDataFlow(appRepository.getCoinPriceInfoFromNet(), coins, TestObserver())
            checkDataFlow(
                appRepository.getLatestNewsArticlesFromNet(), news, TestObserver()
            )
        }
    }
}
