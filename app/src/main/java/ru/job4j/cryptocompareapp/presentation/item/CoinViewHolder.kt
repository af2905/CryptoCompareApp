package ru.job4j.cryptocompareapp.presentation.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

class CoinViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private var coin: Coin? = null
    private var listener: ICoinItemClickListener<Coin>? = null
    private var itemDetail =
        View.OnClickListener { this.coin?.let { it1 -> listener?.openDetail(it1) } }

    fun bind(coin: Coin, listener: ICoinItemClickListener<Coin>) {
        this.coin = coin
        this.listener = listener
        setupItem()
    }

    private fun setupItem() {
        with(view) {
            tvNumber.text = coin?.number.toString()
            tvFullName.text = coin?.coinBasicInfo?.fullName
            tvName.text = coin?.coinBasicInfo?.name
            tvPrice.text = coin?.displayCoinPriceInfo?.coinPriceInfo?.price
            tvChange24.text = coin?.displayCoinPriceInfo?.coinPriceInfo?.change24Hour
            tvChange24Percent.text = coin?.displayCoinPriceInfo?.coinPriceInfo?.changePct24Hour

            val imgUrl = coin?.displayCoinPriceInfo?.coinPriceInfo?.getFullImageUrl()
            Picasso.get().load(imgUrl).into(ivIcon)

            setArrow()
            setOnClickListener(itemDetail)
        }
    }

    private fun setArrow() {
        val change24Hour = coin?.displayCoinPriceInfo?.coinPriceInfo?.change24Hour
        if (change24Hour != null) {
            if (change24Hour.contains('-')) {
                view.ivArrow.setImageResource(android.R.drawable.arrow_down_float)
            } else {
                view.ivArrow.setImageResource(android.R.drawable.arrow_up_float)
            }
        }
    }
}