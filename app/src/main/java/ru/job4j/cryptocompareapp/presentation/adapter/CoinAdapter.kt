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
                    if (key == CoinDiffUtilCallback.PRICE) {
                        txtPrice.text = coin.displayCoinPriceInfo.coinPriceInfo?.price
                    }
                    if (key == CoinDiffUtilCallback.CHANGE_24) {
                        txtChange24.text = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
                    }
                    if (key == CoinDiffUtilCallback.CHANGE_PCT_24) {
                        txtChangePct24.text =
                            coin.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour
                    }
                }
            }
        }
    }

    private fun checkPercentageChangesAndSetArrow(holder: CoinViewHolder, coin: Coin) {
        val change24Hour = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
        if (change24Hour != null) {
            if (change24Hour.contains('-')) {
                holder.imgArrow.setImageResource(android.R.drawable.arrow_down_float)
            } else {
                holder.imgArrow.setImageResource(android.R.drawable.arrow_up_float)
            }
        }
    }
}


/* tvNumber.text = coin?.number.toString()
            tvFullName.text = coin?.coinBasicInfo?.fullName
            tvName.text = coin?.coinBasicInfo?.name
            tvPrice.text = coin?.displayCoinPriceInfo?.coinPriceInfo?.price
            tvChange24.text = coin?.displayCoinPriceInfo?.coinPriceInfo?.change24Hour*/

/* if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals("price")) {
                    holder.mName.setText(data.get(position).name);
                    holder.mPrice.setText(data.get(position).price + " USD");
                    holder.mPrice.setTextColor(Color.GREEN);
                }
            }
        }*/