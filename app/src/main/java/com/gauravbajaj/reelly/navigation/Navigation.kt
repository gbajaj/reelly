package com.gauravbajaj.reelly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gauravbajaj.reelly.ui.screens.VideoGalleryPickerScreen


@Composable
fun ReellyNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Video.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Video.route) {
            VideoGalleryPickerScreen(navController = navController, )
        }
    }
}
