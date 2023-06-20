package com.af2905.cryptotopandnews.presentation.view.topCoins

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.presentation.view.topCoins.callback.TopCoinsActionCallback
import com.af2905.cryptotopandnews.presentation.view.topCoins.item.CoinItem
import com.af2905.cryptotopandnews.theme.dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopCoinsScreen(
    coroutineScope: CoroutineScope,
    list: List<CoinItem>,
    callback: TopCoinsActionCallback
) {
    Surface(color = colorResource(id = R.color.colorConcrete)) {
        Spacer(modifier = Modifier.height(dimens.spaceNormal))
        CoinList(
            coroutineScope = coroutineScope,
            list = list,
            callback = callback
        )
        Spacer(modifier = Modifier.height(dimens.spaceNormal))
    }
}

@Composable
fun CoinList(
    coroutineScope: CoroutineScope,
    list: List<CoinItem>,
    callback: TopCoinsActionCallback
) {
    val coins = remember(list) { list }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimens.spaceSmall),
        contentPadding = PaddingValues(all = dimens.spaceNormal)
    ) {
        items(coins) { item ->
            CoinItem(
                item = item,
                onItemClick = { id ->
                    coroutineScope.launch { callback.onOpenDetailClicked(id = id) }
                }
            )
        }
    }
}

@Composable
fun CoinItem(
    item: CoinItem,
    onItemClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(dimens.radiusSmall),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick.invoke(item.id) }
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.fullName,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.colorPrimaryDark),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = item.name, color = colorResource(id = R.color.colorMako))
                }
                Column(modifier = Modifier.weight(1f)) {
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

fun String.pctChange() = String.format("(%s%%)", this)
fun String.containsMinus(): Boolean = this.contains('-')