package com.af2905.cryptotopandnews.presentation.view.top.state

import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem

class TopCoinsContract {
    sealed interface State {
        object Loading : State
        data class Content(val list: List<CoinItem>) : State
        data class Error(val e: Exception) : State
    }

    sealed interface Action {
        object LoadData : Action
        data class LoadDataSuccess(val list: List<CoinItem>) : Action
        data class LoadDataError(val e: Exception) : Action
        data class OpenDetail(val id: String) : Action
    }

    sealed interface Effect {
        data class OpenDetail(val id: String) : Effect
    }
}