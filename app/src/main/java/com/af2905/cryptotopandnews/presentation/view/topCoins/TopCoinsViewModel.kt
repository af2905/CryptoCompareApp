package com.af2905.cryptotopandnews.presentation.view.topCoins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af2905.cryptotopandnews.domain.usecase.GetTopCoinsUseCase
import com.af2905.cryptotopandnews.presentation.BaseStore
import com.af2905.cryptotopandnews.presentation.view.topCoins.callback.TopCoinsActionCallback
import com.af2905.cryptotopandnews.presentation.view.topCoins.callback.TopCoinsEffectCallback
import com.af2905.cryptotopandnews.presentation.view.topCoins.item.CoinItem
import com.af2905.cryptotopandnews.presentation.view.topCoins.state.TopCoinsContract
import com.af2905.cryptotopandnews.presentation.view.topCoins.state.TopCoinsReducer
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopCoinsViewModel @Inject constructor(
    getTopCoinsUseCase: GetTopCoinsUseCase
) : ViewModel(), TopCoinsActionCallback, TopCoinsEffectCallback {

    private val reducer = TopCoinsReducer(
        getTopCoinsUseCase = getTopCoinsUseCase,
        actionCallback = this,
        effectCallback = this
    )

    val store = BaseStore<TopCoinsContract.State, TopCoinsContract.Action, TopCoinsContract.Effect>(
        initialState = TopCoinsContract.State.Loading,
        scope = viewModelScope,
        reducer = reducer
    )

    init {
        store.subscribe()
        viewModelScope.launch { onLoadData() }
    }

    override suspend fun onLoadData() {
        store.dispatch(TopCoinsContract.Action.LoadData)
    }

    override suspend fun onLoadDataSuccess(list: List<CoinItem>) {
        store.dispatch(TopCoinsContract.Action.LoadDataSuccess(list = list))
    }

    override suspend fun onLoadDataError(throwable: Throwable) {
        store.dispatch(TopCoinsContract.Action.LoadDataError(throwable = throwable))
    }

    override suspend fun onOpenDetailClicked(id: String) {
        store.dispatch(TopCoinsContract.Action.OpenDetail(id = id))
    }

    override suspend fun onOpenDetailScreen(id: String) {
        store.effect(TopCoinsContract.Effect.OpenDetail(id = id))
    }
}