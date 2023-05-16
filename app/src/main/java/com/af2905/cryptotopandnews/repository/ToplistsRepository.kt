package com.af2905.cryptotopandnews.repository

import com.af2905.cryptotopandnews.repository.api.ToplistsApi
import com.af2905.cryptotopandnews.repository.database.dao.CoinDao
import com.af2905.cryptotopandnews.repository.database.entity.CoinEntity
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
        return coinDao.getAll().orEmpty()
    }

    override suspend fun getCoinById(id: String): CoinEntity {
        return coinDao.getCoinById(id)
    }
}