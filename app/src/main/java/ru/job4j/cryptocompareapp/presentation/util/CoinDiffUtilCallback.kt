package ru.job4j.cryptocompareapp.presentation.util

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

class CoinDiffUtilCallback(private val oldList: List<Coin>, private val newList: List<Coin>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCoin: Coin = oldList[oldItemPosition]
        val newCoin: Coin = newList[newItemPosition]
        return oldCoin.displayCoinPriceInfo.coinPriceInfo?.price ==
                newCoin.displayCoinPriceInfo.coinPriceInfo?.price
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldCoin: Coin = oldList[oldItemPosition]
        val newCoin: Coin = newList[newItemPosition]
        val bundle = Bundle()
        if (!(oldCoin.displayCoinPriceInfo.coinPriceInfo?.price
                .equals(newCoin.displayCoinPriceInfo.coinPriceInfo?.price))
        ) {
            bundle.putString(PRICE, newCoin.displayCoinPriceInfo.coinPriceInfo?.price)
        }
        if (!(oldCoin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
                .equals(newCoin.displayCoinPriceInfo.coinPriceInfo?.change24Hour))
        ) {
            bundle.putString(
                CHANGE_24, newCoin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
            )
        }
        if (!(oldCoin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour
                .equals(newCoin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour))
        ) {
            bundle.putString(
                CHANGE_PCT_24, newCoin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour
            )
        }
        if (bundle.size() == 0) {
            return null
        }
        return bundle
    }

    companion object {
        const val PRICE = "price"
        const val CHANGE_24 = "change24Hour"
        const val CHANGE_PCT_24 = "changePct24Hour"
    }
}