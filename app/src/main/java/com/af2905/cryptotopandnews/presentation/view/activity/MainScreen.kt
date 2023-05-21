package com.af2905.cryptotopandnews.presentation.view.activity

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.af2905.cryptotopandnews.presentation.navigation.BottomNavigationItems
import com.af2905.cryptotopandnews.presentation.navigation.SetupNavigationHost


@Composable
fun MainScreen(
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationItems(navController) }
    ) { padding ->
        SetupNavigationHost(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}