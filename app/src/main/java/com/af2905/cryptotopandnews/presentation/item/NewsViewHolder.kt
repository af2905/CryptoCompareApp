package com.af2905.cryptotopandnews.presentation.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.af2905.cryptotopandnews.repository.database.entity.News
import kotlinx.android.synthetic.main.item_news_cardview.view.*

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var news: News
    private lateinit var clickListener: IClickListener<News>
    private val openDetail = View.OnClickListener { clickListener.openDetailInfo(news) }

    val txtSourceInfoName: TextView = itemView.txtSourceInfoName
    val txtNewsPublishedOn: TextView = itemView.txtNewsPublishedOn
    val txtNewsTitle: TextView = itemView.txtNewsTitle
    val txtNewsCategories: TextView = itemView.txtNewsCategories
    val imgNewsIcon: ImageView = itemView.imgNewsIcon

    fun bind(news: News, clickListener: IClickListener<News>) {
        this.news = news
        this.clickListener = clickListener
        itemView.setOnClickListener(openDetail)
    }
}
