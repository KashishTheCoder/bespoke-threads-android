package com.kashish_kirti.bespokethreads.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kashish_kirti.bespokethreads.ui.screens.HomeScreen
import com.kashish_kirti.bespokethreads.ui.screens.ProductDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToProduct = { productId ->
                    // This triggers the navigation and passes the ID in the URL-like route
                    navController.navigate("product_detail/$productId")
                }
            )
        }

        // New route with a dynamic argument: {productId}
        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Extract the ID from the route arguments
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable

            ProductDetailScreen(
                productId = productId,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}