package ru.job4j.cryptocompareapp.presentation.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news_cardview.view.*
import ru.job4j.cryptocompareapp.repository.database.entity.News

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var news: News
    private lateinit var clickListener: IClickListener<News>
    private val openDetail = View.OnClickListener { clickListener.openDetailInfo(news) }

    val txtSourceInfoName: TextView = itemView.txtSourceInfoName
    val txtNewsLastUpdate: TextView = itemView.txtNewsLastUpdate
    val txtNewsTitle: TextView = itemView.txtNewsTitle
    val txtNewsCategories: TextView = itemView.txtNewsCategories
    val imgNewsIcon: ImageView = itemView.imgNewsIcon

    fun bind(news: News, clickListener: IClickListener<News>) {
        this.news = news
        this.clickListener = clickListener
        itemView.setOnClickListener(openDetail)
    }
}
