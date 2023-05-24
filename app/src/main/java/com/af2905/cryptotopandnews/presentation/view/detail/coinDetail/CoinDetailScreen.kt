package com.af2905.cryptotopandnews.presentation.view.detail.coinDetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem
import com.af2905.cryptotopandnews.theme.dimens

@Composable
fun CoinDetailScreen(
    coin: CoinItem
) {
    Surface(color = colorResource(id = R.color.colorConcrete)) {
        Spacer(modifier = Modifier.height(dimens.spaceNormal))
        Text(text = "Coin Item id : ${coin.id}")
        Spacer(modifier = Modifier.height(dimens.spaceNormal))
    }
}