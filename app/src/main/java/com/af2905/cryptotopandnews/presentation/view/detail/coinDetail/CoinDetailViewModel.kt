package com.af2905.cryptotopandnews.presentation.view.detail.coinDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af2905.cryptotopandnews.domain.usecase.CoinDetailParams
import com.af2905.cryptotopandnews.domain.usecase.GetCoinDetailUseCase
import com.af2905.cryptotopandnews.presentation.view.detail.coinDetail.state.Contract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<Contract.CoinDetailState> = MutableStateFlow(Contract.CoinDetailState.Loading)
    val state: StateFlow<Contract.CoinDetailState> = _state

    fun getCoinDetail(id: String) {
        viewModelScope.launch {
            try {
                val response = getCoinDetailUseCase(CoinDetailParams(id)).getOrThrow()
                _state.emit(Contract.CoinDetailState.Content(response))
            } catch (e: Exception) {
                _state.emit(Contract.CoinDetailState.Error(e))
            }
        }
    }
}