package com.af2905.cryptotopandnews.presentation.view.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem
import com.af2905.cryptotopandnews.repository.ToplistsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopCoinsViewModel @Inject constructor(
    val repository: ToplistsRepository
) : ViewModel() {
    private val _topCoins = MutableStateFlow(emptyList<CoinItem>())
    val topCoins: StateFlow<List<CoinItem>> = _topCoins

    init {
        viewModelScope.launch {
            val response = repository.getTopCoins()
            val list = CoinItem.map(response)
            _topCoins.emit(list)
        }
    }
}