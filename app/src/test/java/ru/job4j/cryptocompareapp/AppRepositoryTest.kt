package ru.job4j.cryptocompareapp

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
import ru.job4j.cryptocompareapp.repository.AppRepository
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.entity.News
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

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

        val newsArticles = TestHelper().createListOfNews()
        every { appDatabase.newsDao().getNewsList() } returns newsArticles
        every { serverCommunicator.getLatestNewsArticles() } returns Single.just(
            appDatabase.newsDao().getNewsList()
        )

        val coinsTestSource = appRepository.getCoinPriceInfoFromNet()
        val coinsTestObserver = TestObserver<List<Coin>>()
        coinsTestObserver.assertNotSubscribed()
        coinsTestSource.subscribe(coinsTestObserver)
        coinsTestObserver.awaitTerminalEvent()
        coinsTestObserver.assertComplete()
        coinsTestObserver.assertNoErrors()
        coinsTestObserver.assertValueCount(1)
        coinsTestObserver.assertValues(coins)

        val newsTestSource = appRepository.getLatestNewsArticlesFromNet()
        val newsTestObserver = TestObserver<List<News>>()
        newsTestObserver.assertNotSubscribed()
        newsTestSource.subscribe(newsTestObserver)
        newsTestObserver.awaitTerminalEvent()
        newsTestObserver.assertComplete()
        newsTestObserver.assertNoErrors()
        newsTestObserver.assertValueCount(1)
        newsTestObserver.assertValues(newsArticles)
    }
}
