package com.af2905.cryptotopandnews.repository

import com.af2905.cryptotopandnews.repository.api.ToplistsApi
import com.af2905.cryptotopandnews.repository.database.dao.CoinDao
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import javax.inject.Inject

interface ToplistsRepository {
    suspend fun getToplistBy24HVolumeFullData(): List<Coin>
    suspend fun getCoinById(id: Int): Coin
}

class ToplistsRepositoryImpl @Inject constructor(
    private val toplistsApi: ToplistsApi,
    private val coinDao: CoinDao
) : ToplistsRepository {
    override suspend fun getToplistBy24HVolumeFullData(): List<Coin> {
        //coinDao.insertCoinList(toplistsApi.getToplistBy24HVolumeFullData().coins)
        //return coinDao.getCoinList()
        return toplistsApi.getToplistBy24HVolumeFullData().coins
    }

    override suspend fun getCoinById(id: Int): Coin {
        return coinDao.getCoinById(id)
    }
}