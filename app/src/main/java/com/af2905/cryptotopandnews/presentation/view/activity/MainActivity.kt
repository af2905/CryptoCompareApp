package com.af2905.cryptotopandnews.presentation.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.di.component.AppComponentProvider
import com.af2905.cryptotopandnews.di.daggerViewModel
import com.af2905.cryptotopandnews.presentation.view.detail.CoinDetailScreen
import com.af2905.cryptotopandnews.presentation.view.detail.NewsDetailScreen
import com.af2905.cryptotopandnews.presentation.view.news.NewsScreen
import com.af2905.cryptotopandnews.presentation.view.top.TopCoinsController
import com.af2905.cryptotopandnews.presentation.view.top.TopCoinsViewModel
import com.af2905.cryptotopandnews.presentation.view.top.di.DaggerTopCoinsComponent

class MainActivity : ComponentActivity() {

    //private val appComponent = AppComponentProvider.getAppComponent(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //BottomNavigationExampleTheme {
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val navController: NavHostController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                MainScreen(
                    scaffoldState = scaffoldState,
                    navController = navController
                )
            }
        }
        //}


 /*       setContent {
            val navController = rememberNavController()
            val selectedItem = remember { mutableStateOf("") }

            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
                        content = {
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_finance),
                                        tint = colorResource(id = R.color.colorConcrete),
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = Routes.coins) },
                                selected = selectedItem.value == Routes.coins,
                                onClick = {
                                    navController.navigate(Routes.coins) {
                                        popUpTo(Routes.news) {
                                            saveState = true
                                            inclusive = true
                                        }
                                        restoreState = true
                                    }
                                },
                                alwaysShowLabel = false
                            )
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_news),
                                        tint = colorResource(id = R.color.colorConcrete),
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = Routes.news) },
                                selected = selectedItem.value == Routes.news,
                                onClick = {
                                    navController.navigate(Routes.news) {
                                        popUpTo(Routes.coins) {
                                            saveState = true
                                            inclusive = true
                                        }
                                        restoreState = true
                                    }
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
                            composable(Routes.coins) {
                                val appComponent =
                                    AppComponentProvider.getAppComponent(this@MainActivity)
                                val component =
                                    DaggerTopCoinsComponent.factory().create(appComponent)
                                val viewModel: TopCoinsViewModel = daggerViewModel {
                                    component.getViewModel()
                                }
                                TopCoinsController(
                                    viewModel = viewModel,
                                    onItemClick = { navController.navigate(Routes.coinDetail) }
                                )
                            }

                            composable(Routes.news) {
                                NewsScreen()
                            }
                            composable(Routes.coinDetail) {
                                CoinDetailScreen()
                            }
                            composable(Routes.newsDetail) {
                                NewsDetailScreen()
                            }
                        }
                    }
                }
            )
        }*/
    }
}
