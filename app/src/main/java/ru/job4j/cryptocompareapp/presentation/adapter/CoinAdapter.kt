package ru.job4j.cryptocompareapp.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.presentation.item.CoinViewHolder
import ru.job4j.cryptocompareapp.presentation.item.ICoinItemClickListener
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.CHANGE_24
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.CHANGE_PCT_24
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.PRICE
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

class CoinAdapter : RecyclerView.Adapter<CoinViewHolder>() {
    private var coinPriceInfoList: MutableList<Coin> = mutableListOf()
    var listener: ICoinItemClickListener<Coin>? = null

    fun setData(newList: List<Coin>) {
        val coinDiffUtilCallback = CoinDiffUtilCallback(coinPriceInfoList, newList)
        val coinDiffResult = DiffUtil.calculateDiff(coinDiffUtilCallback)
        coinPriceInfoList.clear()
        coinPriceInfoList.addAll(newList)
        coinDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_coin_info, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount() = coinPriceInfoList.size

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.itemView.tag = coinPriceInfoList[position]
        val coin = coinPriceInfoList[position]
        coin.number = coinPriceInfoList.indexOf(coin) + 1
        listener?.let { holder.bind(coin, it) }

        with(holder) {
            txtNumber.text = coin.number.toString()
            txtFullName.text = coin.coinBasicInfo.fullName
            txtName.text = coin.coinBasicInfo.name
            txtPrice.text = coin.displayCoinPriceInfo.coinPriceInfo?.price
            txtChange24.text = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour

            val imgUrl = coin.displayCoinPriceInfo.coinPriceInfo?.getFullImageUrl()
            Picasso.get().load(imgUrl).into(imgIcon)

            checkPercentageChangesAndSetArrow(holder, coin)

            val percentChanged =
                String.format("(%s%%)", coin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour)
            txtChangePct24.text = percentChanged
        }
    }

    override fun onBindViewHolder(
        holder: CoinViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        holder.itemView.tag = coinPriceInfoList[position]
        val coin = coinPriceInfoList[position]
        coin.number = coinPriceInfoList.indexOf(coin) + 1
        listener?.let { holder.bind(coin, it) }
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
        val res = holder.itemView.resources
        val appTheme = holder.itemView.context.theme

        val change24Hour = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
        if (change24Hour != null) {
            if (change24Hour.contains('-')) {
                holder.imgArrow.setImageDrawable(
                    res.getDrawable(
                        R.drawable.ic_baseline_arrow_drop_down_24,
                        appTheme
                    )
                )
            } else {
                holder.imgArrow.setImageDrawable(
                    res.getDrawable(
                        R.drawable.ic_baseline_arrow_drop_up_24,
                        appTheme
                    )
                )
            }
        }
    }
}