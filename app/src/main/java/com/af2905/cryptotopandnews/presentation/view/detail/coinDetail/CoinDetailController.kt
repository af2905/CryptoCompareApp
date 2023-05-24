package com.af2905.cryptotopandnews.presentation.view.detail.coinDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.af2905.cryptotopandnews.di.ViewModelModule
import com.af2905.cryptotopandnews.presentation.extension.viewModel
import com.af2905.cryptotopandnews.presentation.shared.Progress
import com.af2905.cryptotopandnews.presentation.view.detail.coinDetail.state.Contract

@Composable
fun CoinDetailController(
    id: String,
    viewModelFactory: ViewModelProvider.Factory
) {

    val viewModel: CoinDetailViewModel =
        viewModel(key = ViewModelModule.KEY_COIN_DETAIL) { viewModelFactory }

    val state by viewModel.state.collectAsState()

    when (state) {
        is Contract.CoinDetailState.Loading -> Progress()
        is Contract.CoinDetailState.Content -> CoinDetailScreen(coin = (state as Contract.CoinDetailState.Content).coin)
        is Contract.CoinDetailState.Error -> {}
    }

    LaunchedEffect(Unit) {
        viewModel.getCoinDetail(id)
    }
}