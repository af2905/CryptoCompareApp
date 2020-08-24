package com.af2905.cryptotopandnews.presentation.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import kotlinx.android.synthetic.main.item_coin_info_cardview.view.*

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var coin: Coin
    private lateinit var clickListener: IClickListener<Coin>
    private val openDetail = View.OnClickListener { clickListener.openDetailInfo(coin) }

    val txtFullName: TextView = itemView.txtFullName
    val txtName: TextView = itemView.txtName
    val txtPrice: TextView = itemView.txtPrice
    val txtChange24: TextView = itemView.txtChange24
    val txtChangePct24: TextView = itemView.txtChangePct24
    val imgIcon: ImageView = itemView.imgIcon
    val imgArrow: ImageView = itemView.imgArrow

    fun bind(coin: Coin, clickListener: IClickListener<Coin>) {
        this.coin = coin
        this.clickListener = clickListener
        itemView.setOnClickListener(openDetail)
    }
}
