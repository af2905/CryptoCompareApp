package com.af2905.cryptotopandnews.presentation.view.top

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun TopCoinsController(
    viewModel: TopCoinsViewModel,
    onItemClick: (String) -> Unit
) {
    val topCoins by viewModel.topCoins.collectAsState()

    TopCoinsScreen(list = topCoins, onItemClick = onItemClick)
}