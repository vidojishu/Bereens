package com.vidoit.bereens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen {
                navController.navigate("webview") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable("webview") {
            WebViewScreen()
        }
    }
}
