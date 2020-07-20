package ru.job4j.cryptocompareapp.presentation.item

import android.view.View

interface ICoinItemClickListener<M> : View.OnClickListener {
    fun openDetail(m: M)
}