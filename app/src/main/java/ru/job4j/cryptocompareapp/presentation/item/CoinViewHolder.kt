package ru.job4j.cryptocompareapp.presentation.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_coin_info.view.*
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var coin: Coin? = null
    private var clickListener: ICoinClickListener<Coin>? = null
    private var openDetail =
        View.OnClickListener{ this.coin?.let { it1 -> clickListener?.openDetailInfo(it1) } }

    val txtNumber: TextView = itemView.txtNumber
    val txtFullName: TextView = itemView.txtFullName
    val txtName: TextView = itemView.txtName
    val txtPrice: TextView = itemView.txtPrice
    val txtChange24: TextView = itemView.txtChange24
    val txtChangePct24: TextView = itemView.txtChangePct24
    val imgIcon: ImageView = itemView.imgIcon
    val imgArrow: ImageView = itemView.imgArrow

    fun bind(coin: Coin, clickListener: ICoinClickListener<Coin>) {
        this.coin = coin
        this.clickListener = clickListener
        itemView.setOnClickListener(openDetail)
    }
}