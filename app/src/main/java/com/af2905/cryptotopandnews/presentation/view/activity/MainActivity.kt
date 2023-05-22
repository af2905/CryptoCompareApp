package com.af2905.cryptotopandnews.presentation.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
    }
}
