package com.af2905.cryptotopandnews.presentation.view.detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.theme.dimens

@Composable
fun CoinDetailScreen(navController: NavHostController) {
    Surface(color = colorResource(id = R.color.colorConcrete)) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Coin Detail",
                        color = colorResource(id = R.color.colorWhite)
                    )
                },
                backgroundColor = colorResource(id = R.color.colorPrimaryDark),
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                tint = colorResource(id = R.color.colorWhite),
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }) {
            Spacer(modifier = Modifier.height(dimens.spaceNormal))
            Text(text = "Coin Detail Empty Screen")
            Spacer(modifier = Modifier.height(dimens.spaceNormal))
        }
    }
}

/*@Composable
fun NewsDetailScreen() {
    Surface(color = colorResource(id = R.color.colorConcrete)) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                        color = colorResource(id = R.color.colorWhite)
                    )
                },
                backgroundColor = colorResource(id = R.color.colorPrimaryDark),
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                tint = colorResource(id = R.color.colorWhite),
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }) {
            Spacer(modifier = Modifier.height(dimens.spaceNormal))
            Text(text = "News Detail Empty Screen")
            Spacer(modifier = Modifier.height(dimens.spaceNormal))
        }

    }
}
*/