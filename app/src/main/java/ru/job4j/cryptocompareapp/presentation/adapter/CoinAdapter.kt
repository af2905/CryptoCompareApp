package ru.job4j.cryptocompareapp.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.presentation.item.CoinViewHolder
import ru.job4j.cryptocompareapp.presentation.item.ICoinClickListener
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.CHANGE_24
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.CHANGE_PCT_24
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback.Companion.PRICE
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

class CoinAdapter : RecyclerView.Adapter<CoinViewHolder>() {
    private var coinPriceInfoList: MutableList<Coin> = mutableListOf()
    private var clickListener: ICoinClickListener<Coin>? = null

    fun setData(newList: List<Coin>) {
        val coinDiffUtilCallback = CoinDiffUtilCallback(coinPriceInfoList, newList)
        val coinDiffResult = DiffUtil.calculateDiff(coinDiffUtilCallback)
        coinPriceInfoList.clear()
        coinPriceInfoList.addAll(newList)
        coinDiffResult.dispatchUpdatesTo(this)
    }

    fun setCoinClickListener(clickListener: ICoinClickListener<Coin>?){
        this.clickListener = clickListener
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
        clickListener?.let { holder.bind(coin, it) }

        with(holder) {
            txtNumber.text = coin.number.toString()
            txtFullName.text = coin.coinBasicInfo.fullName
            txtName.text = coin.coinBasicInfo.name
            txtPrice.text = coin.displayCoinPriceInfo.coinPriceInfo?.price
            txtChange24.text = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour

            val imgUrl = coin.displayCoinPriceInfo.coinPriceInfo?.getFullImageUrl()
            //Picasso.get().load(imgUrl).into(imgIcon)
            Glide.with(holder.itemView.context).load(imgUrl).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder_error)
            ).transition(withCrossFade()).into(imgIcon)

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