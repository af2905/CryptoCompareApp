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
        val coins = CoinTestHelper().createListOfCoins()
        every { appDatabase.coinDao().getCoinList() } returns coins
        every { serverCommunicator.getCoinPriceInfo() } returns Single.just(
            appDatabase.coinDao().getCoinList()
        )

        val source = appRepository.getCoinPriceInfoFromNet()
        val testObserver = TestObserver<List<Coin>>()
        testObserver.assertNotSubscribed()
        source.subscribe(testObserver)
        testObserver.awaitTerminalEvent()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertValues(coins)
    }
}