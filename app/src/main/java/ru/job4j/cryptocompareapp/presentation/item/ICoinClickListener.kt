package ru.job4j.cryptocompareapp.presentation.item

interface ICoinClickListener<M> {
    fun openDetailInfo(m: M)
}
