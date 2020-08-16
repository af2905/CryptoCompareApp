package ru.job4j.cryptocompareapp

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.database.pojo.CoinInfoListOfData
import ru.job4j.cryptocompareapp.repository.server.ApiService
import ru.job4j.cryptocompareapp.repository.server.ServerCommunicator

class ServerCommunicatorTest {
    @MockK
    private lateinit var mockApiService: ApiService

    @InjectMockKs
    private lateinit var serverCommunicator: ServerCommunicator

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun `check data flow from the network`() {
        val coins = CoinTestClass().coins
        val priceList = mockk<CoinInfoListOfData>()
        every { priceList.coins } returns coins
        every { mockApiService.getTopCoinsInfo() } returns Single.fromObservable(
            Observable.fromArray(priceList)
        )
        val source = serverCommunicator.getCoinPriceInfo()
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
