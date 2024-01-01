package com.af2905.cryptotopandnews.presentation.view.topCoins

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModelProvider
import com.af2905.cryptotopandnews.di.ViewModelModule
import com.af2905.cryptotopandnews.presentation.extension.viewModel
import com.af2905.cryptotopandnews.presentation.shared.Progress
import com.af2905.cryptotopandnews.presentation.view.topCoins.state.TopCoinsContract

@Composable
fun TopCoinsController(
    viewModelFactory: ViewModelProvider.Factory,
    onItemClick: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val viewModel: TopCoinsViewModel =
        viewModel(key = ViewModelModule.KEY_TOP_COINS) {
            viewModelFactory
        }

    val state by viewModel.store.stateFlow.collectAsState()

    when (state) {
        is TopCoinsContract.State.Loading -> Progress()
        is TopCoinsContract.State.Content -> {
            TopCoinsScreen(
                coroutineScope = coroutineScope,
                list = (state as TopCoinsContract.State.Content).list,
                callback = viewModel
            )
        }

        is TopCoinsContract.State.Error -> {}
    }

    CollectEffectFlow(viewModel = viewModel, onItemClick = onItemClick)
}

@Composable
fun CollectEffectFlow(viewModel: TopCoinsViewModel, onItemClick: (String) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.store.effectFlow.collect { effect ->
            when (effect) {
                is TopCoinsContract.Effect.OpenDetail -> {
                    onItemClick.invoke(effect.id)
                }
            }
        }
    }
}