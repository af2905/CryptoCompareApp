package ru.job4j.cryptocompareapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.job4j.cryptocompareapp.repository.database.AppDatabase
import ru.job4j.cryptocompareapp.repository.database.dao.CoinDao

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    @Rule
    lateinit var instantTaskExecutorRule: InstantTaskExecutorRule

    private lateinit var database: AppDatabase
    private lateinit var coinDao: CoinDao

    @Before
    fun initRoom() {
        instantTaskExecutorRule = InstantTaskExecutorRule()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        coinDao = database.coinDao()
    }

    @After
    fun afterTests() {
        database.close()
    }

    @Test
    fun checkLoadingAndReceivingData() {
        val coins = CoinAndroidTestHelper().coins
        val twoCoinsFromList = listOf(coins[0], coins[1])
        coinDao.insertCoinList(twoCoinsFromList)
        val dbCoins = coinDao.getCoinList()
        assertEquals(2, dbCoins.size)
    }

    @Test
    fun checkReceivingDataById() {
        val coins = CoinAndroidTestHelper().coins
        coinDao.insertCoinList(coins)
        val dbCoins = coinDao.getCoinList()
        val firstCoinId = dbCoins[0].id
        val coin = coinDao.getCoinById(firstCoinId)
        assertEquals(dbCoins[0], coin)
    }
}
