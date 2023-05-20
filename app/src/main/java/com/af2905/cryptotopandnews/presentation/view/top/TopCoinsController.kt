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

/*                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = routePosition.value,
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
                },*/