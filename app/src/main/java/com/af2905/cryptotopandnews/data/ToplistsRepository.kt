package com.af2905.cryptotopandnews.data

import com.af2905.cryptotopandnews.data.api.ToplistsApi
import com.af2905.cryptotopandnews.data.database.dao.CoinDao
import com.af2905.cryptotopandnews.data.database.entity.CoinEntity
import javax.inject.Inject

interface ToplistsRepository {
    suspend fun getTopCoins(): List<CoinEntity>
    suspend fun getCoinById(id: String): CoinEntity
}

class ToplistsRepositoryImpl @Inject constructor(
    private val toplistsApi: ToplistsApi,
    private val coinDao: CoinDao
) : ToplistsRepository {
    override suspend fun getTopCoins(): List<CoinEntity> {
        val list = toplistsApi.getToplistBy24HVolumeFullData().coins
        coinDao.save(CoinEntity.map(list))
        val savedList = coinDao.getAll().orEmpty()
        //TODO need to move filterNot to use case
        return savedList.filterNot { it.displayCoinPriceInfo == null }
    }

    override suspend fun getCoinById(id: String): CoinEntity {
        return coinDao.getCoinById(id)
    }
}