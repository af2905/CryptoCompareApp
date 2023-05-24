package com.af2905.cryptotopandnews.presentation.view.top

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.af2905.cryptotopandnews.di.ViewModelModule
import com.af2905.cryptotopandnews.presentation.extension.viewModel
import com.af2905.cryptotopandnews.presentation.shared.Progress
import com.af2905.cryptotopandnews.presentation.view.top.state.Contract

@Composable
fun TopCoinsController(
    viewModelFactory: ViewModelProvider.Factory,
    onItemClick: (String) -> Unit
) {

    val viewModel: TopCoinsViewModel =
        viewModel(key = ViewModelModule.KEY_TOP_COINS) { viewModelFactory }

    val state by viewModel.state.collectAsState()

    when(state) {
        is Contract.TopCoinsState.Loading -> Progress()
        is Contract.TopCoinsState.Content -> {
            TopCoinsScreen(list = (state as Contract.TopCoinsState.Content).list, onItemClick = onItemClick)
        }
        is Contract.TopCoinsState.Error -> {}
    }
}