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
        val oldPrice = oldCoin.displayCoinPriceInfo.coinPriceInfo?.price
        val newPrice = newCoin.displayCoinPriceInfo.coinPriceInfo?.price
        return oldPrice == newPrice && oldCoin.number == newCoin.number
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldCoin: Coin = oldList[oldItemPosition]
        val newCoin: Coin = newList[newItemPosition]
        val oldPrice = oldCoin.displayCoinPriceInfo.coinPriceInfo?.price
        val newPrice = newCoin.displayCoinPriceInfo.coinPriceInfo?.price
        val oldChange24Hour = oldCoin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
        val newChange24Hour = newCoin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
        val oldChangePct24Hour = oldCoin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour
        val newChangePct24Hour = newCoin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour

        val bundle = Bundle()
        if (!(oldPrice.equals(newPrice))) {
            bundle.putString(PRICE, newPrice)
        }
        if (!(oldChange24Hour.equals(newChange24Hour))) {
            bundle.putString(CHANGE_24, newChange24Hour)
        }
        if (!(oldChangePct24Hour.equals(newChangePct24Hour))) {
            bundle.putString(CHANGE_PCT_24, newChangePct24Hour)
        }
        if (oldCoin.number != newCoin.number) {
            bundle.putString(NUMBER, newCoin.number.toString())
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
        const val NUMBER = "number"
    }
}