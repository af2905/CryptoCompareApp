package ru.job4j.cryptocompareapp.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.presentation.item.CoinViewHolder
import ru.job4j.cryptocompareapp.presentation.item.ICoinClickListener
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.CHANGE_24
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.CHANGE_PCT_24
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.PRICE
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.server.GlideClient

class CoinAdapter : RecyclerView.Adapter<CoinViewHolder>() {
    var coinList: MutableList<Coin> = mutableListOf()
    private var clickListener: ICoinClickListener<Coin>? = null

    fun setCoinClickListener(clickListener: ICoinClickListener<Coin>?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_coin_info_cardview, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount() = coinList.size

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.itemView.tag = coinList[position]
        val coin = coinList[position]
        clickListener?.let { holder.bind(coin, it) }

        with(holder) {
            txtNumber.text = coin.number.toString()
            txtFullName.text = coin.coinBasicInfo.fullName
            txtName.text = coin.coinBasicInfo.name
            txtPrice.text = coin.displayCoinPriceInfo.coinPriceInfo?.price
            txtChange24.text = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
            val imgUrl = coin.displayCoinPriceInfo.coinPriceInfo?.getFullImageUrl()
            if (imgUrl != null) {
                GlideClient.downloadImage(holder.itemView.context, imgUrl, imgIcon)
            }
            checkPercentageChangesAndSetArrow(holder, coin)
            txtChangePct24.text =
                String.format("(%s%%)", coin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour)
        }
    }

    override fun onBindViewHolder(
        holder: CoinViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        holder.itemView.tag = coinList[position]
        val coin = coinList[position]
        coin.number = coinList.indexOf(coin) + 1
        clickListener?.let { holder.bind(coin, it) }
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle: Bundle = payloads.firstOrNull() as Bundle
            for (key in bundle.keySet()) {
                with(holder) {
                    when (key) {
                        PRICE -> txtPrice.text = coin.displayCoinPriceInfo.coinPriceInfo?.price
                        CHANGE_24 -> txtChange24.text =
                            coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
                        CHANGE_PCT_24 -> txtChangePct24.text =
                            coin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour
                    }
                }
            }
        }
    }

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
}