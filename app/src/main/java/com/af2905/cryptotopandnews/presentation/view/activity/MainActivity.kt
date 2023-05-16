package com.af2905.cryptotopandnews.presentation.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.presentation.view.news.NewsScreen
import com.af2905.cryptotopandnews.presentation.view.top.TopCoinsScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val selectedItem = remember { mutableStateOf("") }
            val routePosition = remember {
                mutableStateOf(Routes.coins)
            }
            Scaffold(
                topBar = {
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
                },
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
                        content = {
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_finance),
                                        tint = if (routePosition.value == Routes.coins) {
                                            colorResource(id = R.color.colorAccent)
                                        } else {
                                            colorResource(id = R.color.colorConcrete)
                                        },
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = Routes.coins) },
                                selected = selectedItem.value == Routes.coins,
                                onClick = {
                                    routePosition.value = Routes.coins
                                    navController.navigate(Routes.coins)
                                },
                                alwaysShowLabel = false,
                            )
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_news),
                                        tint = if (routePosition.value == Routes.news) {
                                            colorResource(id = R.color.colorAccent)
                                        } else {
                                            colorResource(id = R.color.colorConcrete)
                                        },
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = Routes.news) },
                                selected = selectedItem.value == Routes.news,
                                onClick = {
                                    routePosition.value = Routes.news
                                    navController.navigate(Routes.news)
                                },
                                alwaysShowLabel = false
                            )
                        }
                    )
                },
                content = {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(
                            navController = navController,
                            startDestination = Routes.coins,
                        ) {
                            composable(Routes.coins) { TopCoinsScreen() }
                            composable(Routes.news) { NewsScreen() }
                        }
                    }
                }
            )
        }
    }

    internal object Routes {
        const val coins: String = "Coins"
        const val news: String = "News"
        const val coinDetail: String = "Coin Detail"
        const val newsDetail: String = "News Detail"
    }

}
