package com.af2905.cryptotopandnews.presentation.view.top

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.af2905.cryptotopandnews.di.ViewModelModule
import com.af2905.cryptotopandnews.presentation.extension.viewModel

@Composable
internal fun TopCoinsController(
    viewModelFactory: ViewModelProvider.Factory,
    onItemClick: (String) -> Unit
) {

    val viewModel: TopCoinsViewModel =
        viewModel(key = ViewModelModule.KEY_TOP_COINS) { viewModelFactory }

    val topCoins by viewModel.topCoins.collectAsState()

    TopCoinsScreen(list = topCoins, onItemClick = onItemClick)
}