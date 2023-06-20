package com.af2905.cryptotopandnews.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.di.component.AppComponentProvider
import com.af2905.cryptotopandnews.presentation.navigation.Routes.COIN_DETAIL_SCREEN
import com.af2905.cryptotopandnews.presentation.navigation.Routes.NEWS_DETAIL_SCREEN
import com.af2905.cryptotopandnews.presentation.navigation.Routes.NEWS_SCREEN
import com.af2905.cryptotopandnews.presentation.navigation.Routes.TOP_COINS_SCREEN
import com.af2905.cryptotopandnews.presentation.view.detail.coinDetail.CoinDetailController
import com.af2905.cryptotopandnews.presentation.view.detail.newsDetail.NewsDetailScreen
import com.af2905.cryptotopandnews.presentation.view.news.NewsScreen
import com.af2905.cryptotopandnews.presentation.view.topCoins.TopCoinsController

data class Item(
    val route: String,
    @DrawableRes val icon: Int
)

@Composable
fun BottomNavigationItems(navController: NavController) {
    val itemList = listOf(
        Item(route = TOP_COINS_SCREEN, icon = R.drawable.ic_finance),
        Item(route = NEWS_SCREEN, icon = R.drawable.ic_news)
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorPrimaryDark)
    ) {
        val destinationChanged: MutableState<String?> = remember { mutableStateOf(null) }
        val isInParentRoute = itemList.firstOrNull { it.route == destinationChanged.value } != null
        val parentRoute: MutableState<String?> =
            remember(destinationChanged.value) { mutableStateOf(TOP_COINS_SCREEN) }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        fun selectedBottomNavigationItem() = if (isInParentRoute) {
            parentRoute.value = currentRoute
            currentRoute
        } else {
            parentRoute.value
        }

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            destinationChanged.value = navDestination.route
        }

        itemList.forEach { item ->
            val isSelected = selectedBottomNavigationItem() == item.route
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = null) },
                selectedContentColor = colorResource(R.color.colorAccent),
                unselectedContentColor = colorResource(R.color.colorConcrete),
                alwaysShowLabel = false,
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun TopBarNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = when (currentRoute) {
        TOP_COINS_SCREEN -> stringResource(id = R.string.screen_top_coins_title)
        NEWS_SCREEN -> stringResource(id = R.string.screen_news_title)
        COIN_DETAIL_SCREEN -> stringResource(id = R.string.screen_coin_detail_title)
        NEWS_DETAIL_SCREEN -> stringResource(id = R.string.screen_news_detail_title)
        else -> ""
    }

    val bottomNavigationItemRouteList = listOf(TOP_COINS_SCREEN, NEWS_SCREEN)

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.colorWhite)
            )
        },
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        navigationIcon = if (navController.previousBackStackEntry != null &&
            !bottomNavigationItemRouteList.contains(currentRoute)
        ) {
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
}

@Composable
fun SetupNavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val appComponent = AppComponentProvider.getAppComponent(context)
    val viewModelFactory = appComponent.getViewModelFactory()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TOP_COINS_SCREEN
    ) {
        composable(TOP_COINS_SCREEN) {
            TopCoinsController(
                viewModelFactory = viewModelFactory,
                onItemClick = { id ->
                    navController.navigate(COIN_DETAIL_SCREEN.replace("{id}", id))
                }
            )
        }
        composable(
            route = COIN_DETAIL_SCREEN,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->
                CoinDetailController(id = id, viewModelFactory = viewModelFactory)
            }
        }
        composable(NEWS_SCREEN) {
            NewsScreen()
        }

        composable(NEWS_DETAIL_SCREEN) {
            NewsDetailScreen()
        }
    }
}

object Routes {
    const val TOP_COINS_SCREEN: String = "top_coins"
    const val NEWS_SCREEN: String = "news"
    const val COIN_DETAIL_SCREEN: String = "coin_detail/{id}"
    const val NEWS_DETAIL_SCREEN: String = "news_detail"
}
