package com.af2905.cryptotopandnews.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.af2905.cryptotopandnews.data.database.dto.Coin
import com.af2905.cryptotopandnews.data.database.dto.CoinBasicInfo
import com.af2905.cryptotopandnews.data.database.dto.DisplayCoinPriceInfo

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey
    val primaryKey: String,
    @Embedded
    val coinBasicInfo: CoinBasicInfo,
    @Embedded
    val displayCoinPriceInfo: DisplayCoinPriceInfo?
) {
    companion object {
        fun map(list: List<Coin>): List<CoinEntity> {
            return list.map { map(it) }
        }

        private fun map(coin: Coin): CoinEntity {
            return CoinEntity(
                primaryKey = coin.coinBasicInfo.id,
                coinBasicInfo = coin.coinBasicInfo,
                displayCoinPriceInfo = coin.displayCoinPriceInfo
            )
        }
    }
}