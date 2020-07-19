package ru.job4j.cryptocompareapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.presentation.item.CoinViewHolder
import ru.job4j.cryptocompareapp.presentation.item.ICoinItemClickListener
import ru.job4j.cryptocompareapp.repository.database.entity.Coin

class CoinAdapter : RecyclerView.Adapter<CoinViewHolder>() {
    var coinPriceInfoList: List<Coin> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ICoinItemClickListener<Coin>? = null


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
    }
}