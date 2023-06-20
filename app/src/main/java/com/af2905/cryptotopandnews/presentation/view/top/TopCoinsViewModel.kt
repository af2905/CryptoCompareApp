package com.af2905.cryptotopandnews.presentation.view.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af2905.cryptotopandnews.domain.usecase.GetTopCoinsUseCase
import com.af2905.cryptotopandnews.presentation.BaseStore
import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem
import com.af2905.cryptotopandnews.presentation.view.top.state.TopCoinsContract
import com.af2905.cryptotopandnews.presentation.view.top.state.TopCoinsReducer
import com.af2905.cryptotopandnews.presentation.view.top.state.TopCoinsReducerCallback
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopCoinsViewModel @Inject constructor(
    getTopCoinsUseCase: GetTopCoinsUseCase
) : ViewModel(), TopCoinsReducerCallback {

    private val reducer = TopCoinsReducer(getTopCoinsUseCase = getTopCoinsUseCase, callback = this)

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

    override suspend fun onLoadDataError(e: Exception) {
        store.dispatch(TopCoinsContract.Action.LoadDataError(e = e))
    }

    override suspend fun onOpenDetailClicked(id: String) {
        store.dispatch(TopCoinsContract.Action.OpenDetail(id = id))
    }

    override suspend fun openDetailScreen(id: String) {
        store.effect(TopCoinsContract.Effect.OpenDetail(id = id))
    }
}