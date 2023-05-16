package com.af2905.cryptotopandnews.presentation.view.top.item

import com.af2905.cryptotopandnews.repository.database.entity.Coin

data class CoinItem(
    val id: String,
    val name: String,
    val fullName: String,
    val price: String,
    val change24Hour: String,
    val changePct24Hour: String,
    val imageUrl: String
) {
    companion object {
        fun map(list: List<Coin>): List<CoinItem> {
            return list.map { map(it) }
        }

        private fun map(coin: Coin): CoinItem {
            return with(coin) {
                CoinItem(
                    id = coinBasicInfo.coinBasicId,
                    fullName = coinBasicInfo.fullName.orEmpty(),
                    name = coinBasicInfo.name.orEmpty(),
                    price = displayCoinPriceInfo.coinPriceInfo?.price.orEmpty(),
                    change24Hour = displayCoinPriceInfo.coinPriceInfo?.change24Hour.orEmpty(),
                    changePct24Hour = displayCoinPriceInfo.coinPriceInfo?.changePct24Hour.orEmpty(),
                    imageUrl = displayCoinPriceInfo.coinPriceInfo?.getFullImageUrl().orEmpty()

                )
            }
        }
    }
}