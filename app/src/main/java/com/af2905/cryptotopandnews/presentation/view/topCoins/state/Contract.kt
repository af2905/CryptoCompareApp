package com.af2905.cryptotopandnews.presentation.view.topCoins.state

import com.af2905.cryptotopandnews.presentation.view.topCoins.item.CoinItem

class TopCoinsContract {
    sealed interface State {
        object Loading : State
        data class Content(val list: List<CoinItem>) : State
        data class Error(val throwable: Throwable) : State
    }

    sealed interface Action {
        object LoadData : Action
        data class LoadDataSuccess(val list: List<CoinItem>) : Action
        data class LoadDataError(val throwable: Throwable) : Action
        data class OpenDetail(val id: String) : Action
    }

    sealed interface Effect {
        data class OpenDetail(val id: String) : Effect
    }
}