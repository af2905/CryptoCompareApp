package com.af2905.cryptotopandnews.presentation.view.top.state

import com.af2905.cryptotopandnews.domain.usecase.GetTopCoinsUseCase
import com.af2905.cryptotopandnews.domain.usecase.TopCoinsParams
import com.af2905.cryptotopandnews.presentation.Reducer

class TopCoinsReducer(
    private val getTopCoinsUseCase: GetTopCoinsUseCase,
    private val callback: TopCoinsReducerCallback
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
                return try {
                    val response = getTopCoinsUseCase(TopCoinsParams).getOrThrow()
                    callback.onLoadDataSuccess(list = response)
                    state
                } catch (e: Exception) {
                    callback.onLoadDataError(e = e)
                    state
                }
            }

            is TopCoinsContract.Action.LoadDataSuccess -> {
                TopCoinsContract.State.Content(list = action.list)
            }

            is TopCoinsContract.Action.LoadDataError -> {
                TopCoinsContract.State.Error(e = action.e)
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
                callback.openDetailScreen(id = action.id)
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
                callback.onLoadData()
                TopCoinsContract.State.Loading
            }

            else -> state
        }
    }
}