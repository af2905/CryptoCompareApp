package com.af2905.cryptotopandnews.presentation.view.top

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.presentation.view.top.item.CoinItem
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.pojo.CoinBasicInfo
import com.af2905.cryptotopandnews.repository.database.pojo.CoinPriceInfo
import com.af2905.cryptotopandnews.repository.database.pojo.DisplayCoinPriceInfo
import com.af2905.cryptotopandnews.repository.database.pojo.RawCoinPriceInfo
import com.af2905.cryptotopandnews.theme.dimens

@Composable
fun TopCoinsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "CoinList", color = colorResource(id = R.color.colorWhite))
                },
                backgroundColor = colorResource(id = R.color.colorPrimaryDark)
            )
        },
        content = {
            Surface(color = colorResource(id = R.color.colorConcrete)) {
                Spacer(modifier = Modifier.height(dimens.spaceNormal))
                CoinList()
                Spacer(modifier = Modifier.height(dimens.spaceNormal))
            }
        }
    )
}

@Composable
fun CoinList() {
    val list = mutableListOf<CoinItem>()
    for (i in 0..30) {
        val coin = Coin(
            id = i,
            coinBasicInfo = CoinBasicInfo(
                coinBasicId = i.toString(),
                name = "WBTH",
                fullName = "Bitcoin"
            ),
            rawCoinPriceInfo = RawCoinPriceInfo(),
            displayCoinPriceInfo = DisplayCoinPriceInfo(
                coinPriceInfo = CoinPriceInfo(
                    price = "$ 28,271.6",
                    change24Hour = "$ 189.90",
                    changePct24Hour = if (i % 2 == 0) "-0.68" else "0.50",
                    imageUrl = "/media/37746251/btc.png"
                )
            )
        )
        list.add(
            CoinItem.map(coin)
        )
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimens.spaceSmall),
        contentPadding = PaddingValues(all = dimens.spaceNormal)
    ) {
        items(list) { item ->
            CoinItem(
                item = item,
                onItemClick = { id ->
                    Log.d("ITEM_CLICKED_ID", "$id")
                }
            )
        }
    }
}

@Composable
fun CoinItem(
    item: CoinItem,
    onItemClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(dimens.radiusSmall),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke(item.id)
            }
    ) {
        Row {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = dimens.spaceSmall)
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    modifier = Modifier
                        .width(dimens.imageNormal)
                        .height(dimens.imageNormal),
                    contentDescription = null
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimens.spaceTiny, horizontal = dimens.spaceNormal)
            ) {
                Column() {
                    Text(
                        text = item.fullName,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.colorPrimaryDark)
                    )
                    Text(text = item.name, color = colorResource(id = R.color.colorMako))
                }
                Column {
                    Text(
                        text = item.price,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.colorPrimaryDark),
                        modifier = Modifier.align(Alignment.End)
                    )
                    Row(modifier = Modifier.align(Alignment.End)) {
                        Text(text = "${item.change24Hour} ${item.changePct24Hour.pctChange()}")
                        Icon(
                            painter = if (item.changePct24Hour.containsMinus()) {
                                painterResource(id = R.drawable.ic_arrow_down)
                            } else painterResource(
                                id = R.drawable.ic_arrow_up
                            ),
                            tint = if (item.changePct24Hour.containsMinus()) Color.Red else Color.Green,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TopCoinsScreenPreview() {
    TopCoinsScreen()
}

fun String.pctChange() = String.format("(%s%%)", this)
fun String.containsMinus(): Boolean = this.contains('-')