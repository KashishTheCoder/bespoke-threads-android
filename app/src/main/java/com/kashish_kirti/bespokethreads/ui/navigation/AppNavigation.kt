package com.kashish_kirti.bespokethreads.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kashish_kirti.bespokethreads.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToProduct = { productId ->
                    // Future implementation for product details
                }
            )
        }
        // Future routes: "cart", "profile", "checkout" will go here
    }
}