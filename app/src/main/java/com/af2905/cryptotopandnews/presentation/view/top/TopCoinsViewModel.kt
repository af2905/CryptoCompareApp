package com.af2905.cryptotopandnews.presentation.view.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af2905.cryptotopandnews.domain.usecase.GetTopCoinsUseCase
import com.af2905.cryptotopandnews.domain.usecase.TopCoinsParams
import com.af2905.cryptotopandnews.presentation.view.top.state.Contract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopCoinsViewModel @Inject constructor(
    private val getTopCoinsUseCase: GetTopCoinsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<Contract.TopCoinsState> = MutableStateFlow(Contract.TopCoinsState.Loading)
    val state: StateFlow<Contract.TopCoinsState> = _state

    init {
        viewModelScope.launch {
            try {
                val response = getTopCoinsUseCase(TopCoinsParams).getOrThrow()
                _state.emit(Contract.TopCoinsState.Content(response))
            } catch (e: Exception) {
                Contract.TopCoinsState.Error(e)
            }
        }
    }
}