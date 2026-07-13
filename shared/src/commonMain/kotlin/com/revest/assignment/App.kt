package com.revest.assignment

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.revest.assignment.presentation.ProductDetailScreen
import com.revest.assignment.presentation.ProductListScreen
import com.revest.assignment.presentation.ProductViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object ProductListRoute

@Serializable
data class ProductDetailRoute(val productId: Int)

@Composable
fun App() {
    MaterialTheme {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = ProductListRoute
            ) {
                composable<ProductListRoute> {
                    val viewModel: ProductViewModel = koinViewModel()
                    ProductListScreen(
                        viewModel = viewModel,
                        onProductClick = { productId ->
                            navController.navigate(ProductDetailRoute(productId))
                        }
                    )
                }
                composable<ProductDetailRoute> { backStackEntry ->
                    val route: ProductDetailRoute = backStackEntry.toRoute()
                    val viewModel: ProductViewModel = koinViewModel()
                    ProductDetailScreen(
                        productId = route.productId,
                        viewModel = viewModel,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }

