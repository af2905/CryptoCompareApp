package com.af2905.cryptotopandnews.presentation.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.presentation.item.CoinViewHolder
import com.af2905.cryptotopandnews.presentation.item.IClickListener
import com.af2905.cryptotopandnews.presentation.utils.CoinDiffUtilCallback.Companion.CHANGE_24
import com.af2905.cryptotopandnews.presentation.utils.CoinDiffUtilCallback.Companion.CHANGE_PCT_24
import com.af2905.cryptotopandnews.presentation.utils.CoinDiffUtilCallback.Companion.PRICE
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.server.GlideClient

class CoinAdapter : RecyclerView.Adapter<CoinViewHolder>() {
    private var coins: MutableList<Coin> = mutableListOf()
    private lateinit var clickListener: IClickListener<Coin>

    fun setClickListener(clickListener: IClickListener<Coin>) {
        this.clickListener = clickListener
    }

    fun getCoins() = coins

    fun setCoins(coins: List<Coin>) {
        this.coins = coins as MutableList<Coin>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_coin_info_cardview, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount() = coins.size

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.itemView.tag = coins[position]
        val coin = coins[position]
        holder.bind(coin, clickListener)

        with(holder) {
            txtFullName.text = coin.coinBasicInfo.fullName
            txtName.text = coin.coinBasicInfo.name
            coin.displayCoinPriceInfo.coinPriceInfo?.let {
                txtPrice.text = it.price
                txtChange24.text = it.change24Hour
                val imgUrl = it.getFullImageUrl()
                GlideClient.downloadImage(itemView.context, imgUrl, imgIcon)
                txtChangePct24.text = pctChange(it.changePct24Hour)
            }
            checkPercentageChangesAndSetArrow(holder, coin)
        }
    }

    override fun onBindViewHolder(
        holder: CoinViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.itemView.tag = coins[position]
        val coin = coins[position]
        holder.bind(coin, clickListener)
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle: Bundle = payloads.firstOrNull() as Bundle
            for (key in bundle.keySet()) {
                with(holder) {
                    coin.displayCoinPriceInfo.coinPriceInfo?.let {
                        when (key) {
                            PRICE -> txtPrice.text = it.price
                            CHANGE_24 -> txtChange24.text = it.change24Hour
                            CHANGE_PCT_24 -> txtChangePct24.text = pctChange(it.changePct24Hour)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkPercentageChangesAndSetArrow(holder: CoinViewHolder, coin: Coin) {
        val change24Hour = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
        if (change24Hour != null) {
            with(holder) {
                val res = itemView.resources
                val appTheme = itemView.context.theme
                if (change24Hour.contains('-')) {
                    imgArrow.setImageDrawable(res.getDrawable(R.drawable.ic_arrow_down, appTheme))
                } else {
                    imgArrow.setImageDrawable(res.getDrawable(R.drawable.ic_arrow_up, appTheme))
                }
            }
        }
    }

    private fun pctChange(text: String?) = String.format("(%s%%)", text)
}
