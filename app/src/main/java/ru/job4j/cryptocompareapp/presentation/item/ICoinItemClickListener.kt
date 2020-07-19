package ru.job4j.cryptocompareapp.presentation.item

interface ICoinItemClickListener<M> {
    fun openDetail(m: M)
}