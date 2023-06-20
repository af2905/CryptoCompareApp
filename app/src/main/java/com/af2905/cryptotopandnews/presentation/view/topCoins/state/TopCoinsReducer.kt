package com.af2905.cryptotopandnews.presentation.view.topCoins.state

import com.af2905.cryptotopandnews.domain.usecase.GetTopCoinsUseCase
import com.af2905.cryptotopandnews.domain.usecase.TopCoinsParams
import com.af2905.cryptotopandnews.presentation.Reducer
import com.af2905.cryptotopandnews.presentation.view.topCoins.callback.TopCoinsActionCallback
import com.af2905.cryptotopandnews.presentation.view.topCoins.callback.TopCoinsEffectCallback

class TopCoinsReducer(
    private val getTopCoinsUseCase: GetTopCoinsUseCase,
    private val actionCallback: TopCoinsActionCallback,
    private val effectCallback: TopCoinsEffectCallback
) : Reducer<TopCoinsContract.State, TopCoinsContract.Action> {
    override suspend fun reduce(
        state: TopCoinsContract.State,
        action: TopCoinsContract.Action
    ): TopCoinsContract.State {
        return when (state) {
            is TopCoinsContract.State.Loading -> handleLoadingState(state = state, action = action)
            is TopCoinsContract.State.Content -> handleContentState(state = state, action = action)
            is TopCoinsContract.State.Error -> handleErrorState(state = state, action = action)
        }
    }

    private suspend fun handleLoadingState(
        state: TopCoinsContract.State.Loading,
        action: TopCoinsContract.Action
    ): TopCoinsContract.State {
        return when (action) {
            is TopCoinsContract.Action.LoadData -> {
                getTopCoinsUseCase(TopCoinsParams).fold(
                    onSuccess = { list ->
                        actionCallback.onLoadDataSuccess(list = list)
                        state
                    },
                    onFailure = { error ->
                        actionCallback.onLoadDataError(throwable = error)
                        state
                    }
                )
            }

            is TopCoinsContract.Action.LoadDataSuccess -> {
                TopCoinsContract.State.Content(list = action.list)
            }

            is TopCoinsContract.Action.LoadDataError -> {
                TopCoinsContract.State.Error(throwable = action.throwable)
            }

            else -> state
        }
    }

    private suspend fun handleContentState(
        state: TopCoinsContract.State.Content,
        action: TopCoinsContract.Action
    ): TopCoinsContract.State {
        return when (action) {
            is TopCoinsContract.Action.OpenDetail -> {
                effectCallback.onOpenDetailScreen(id = action.id)
                state
            }

            else -> state
        }
    }

    private suspend fun handleErrorState(
        state: TopCoinsContract.State.Error,
        action: TopCoinsContract.Action
    ): TopCoinsContract.State {
        return when (action) {
            is TopCoinsContract.Action.LoadData -> {
                actionCallback.onLoadData()
                TopCoinsContract.State.Loading
            }

            else -> state
        }
    }
}