package com.kashish_kirti.bespokethreads.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kashish_kirti.bespokethreads.ui.screens.HomeScreen
import com.kashish_kirti.bespokethreads.ui.screens.ProductDetailScreen
import com.kashish_kirti.bespokethreads.ui.screens.CartScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToProduct = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onNavigateToCart = {
                    navController.navigate("cart")
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
        composable("cart") {
            CartScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}