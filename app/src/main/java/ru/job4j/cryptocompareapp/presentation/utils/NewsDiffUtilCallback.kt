package ru.job4j.cryptocompareapp.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import ru.job4j.cryptocompareapp.repository.database.entity.News

class NewsDiffUtilCallback(private val oldList: List<News>, private val newList: List<News>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].publishedOn == newList[newItemPosition].publishedOn
                && oldList[oldItemPosition].title == newList[newItemPosition].title
    }
}