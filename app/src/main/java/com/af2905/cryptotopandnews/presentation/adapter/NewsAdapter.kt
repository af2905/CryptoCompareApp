package com.af2905.cryptotopandnews.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.presentation.item.IClickListener
import com.af2905.cryptotopandnews.presentation.item.NewsViewHolder
import com.af2905.cryptotopandnews.presentation.utils.TimeUtils
import com.af2905.cryptotopandnews.repository.database.entity.News
import com.af2905.cryptotopandnews.repository.server.GlideClient

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {
    private var newsArticles: MutableList<News> = mutableListOf()
    private lateinit var clickListener: IClickListener<News>

    fun setClickListener(clickListener: IClickListener<News>) {
        this.clickListener = clickListener
    }

    fun getNewsArticles() = newsArticles

    fun setNewsArticles(newsArticles: List<News>) {
        this.newsArticles = newsArticles as MutableList<News>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_news_cardview, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount() = newsArticles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.tag = newsArticles[position]
        val news = newsArticles[position]
        holder.bind(news, clickListener)

        with(holder) {
            txtNewsPublishedOn.text = news.publishedOn?.let { TimeUtils.convertTimestampToTime(it) }
            txtNewsTitle.text = news.title
            txtNewsCategories.text = news.categories
            news.newsSourceInfo?.let { txtSourceInfoName.text = it.name }
            news.imageUrl?.let { GlideClient.downloadImage(itemView.context, it, imgNewsIcon) }
        }
    }
}
