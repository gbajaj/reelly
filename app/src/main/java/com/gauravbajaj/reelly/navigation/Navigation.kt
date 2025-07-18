package com.gauravbajaj.reelly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gauravbajaj.reelly.ui.screens.SplashScreen
import com.gauravbajaj.reelly.ui.screens.VideoGalleryPickerScreen


@Composable
fun ReellyNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen() {
                // Navigate to video player screen and remove splash from back stack
                navController.navigate(Screen.Video.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }
        composable(Screen.Video.route) {
            VideoGalleryPickerScreen(navController = navController)
        }
    }
}
