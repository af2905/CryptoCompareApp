package com.af2905.cryptotopandnews

import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.pojo.CoinsListOfData
import com.af2905.cryptotopandnews.repository.server.ApiService
import com.af2905.cryptotopandnews.repository.server.ServerCommunicator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test

class ServerCommunicatorTest {
    @MockK
    private lateinit var apiService: ApiService

    @InjectMockKs
    private lateinit var serverCommunicator: ServerCommunicator

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @After
    fun afterTests() {
        unmockkAll()
    }

    @Test
    fun checkDataFlowFromApiService() {
        val coins = TestHelper().createListOfCoins()
        val priceList = mockk<CoinsListOfData>()
        every { priceList.coins } returns coins
        every { apiService.getTopCoinsInfo() } returns Single.fromObservable(
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
