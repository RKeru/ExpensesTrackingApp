package com.rkeru.expensesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rkeru.expensesapp.ui.dashboard.DashboardDestination
import com.rkeru.expensesapp.ui.dashboard.DashboardScreen
import com.rkeru.expensesapp.ui.home.HomeDestination
import com.rkeru.expensesapp.ui.home.HomeScreen
import com.rkeru.expensesapp.ui.settings.SettingDestination
import com.rkeru.expensesapp.ui.settings.SettingsScreen
import com.rkeru.expensesapp.ui.settings.category.CategoryDetailDestination
import com.rkeru.expensesapp.ui.settings.category.CategoryDetailScreen
import com.rkeru.expensesapp.ui.settings.category.CategoryEntryDestination
import com.rkeru.expensesapp.ui.settings.category.CategoryEntryScreen
import com.rkeru.expensesapp.ui.settings.category.CategoryListDestination
import com.rkeru.expensesapp.ui.settings.category.CategoryListScreen
import com.rkeru.expensesapp.ui.transaction.TransactionDetailsDestination
import com.rkeru.expensesapp.ui.transaction.TransactionDetailsScreen
import com.rkeru.expensesapp.ui.transaction.TransactionEntryDestination
import com.rkeru.expensesapp.ui.transaction.TransactionEntryScreen


@Composable
fun ExpensesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToEntry = { navController.navigate(TransactionEntryDestination.route) },
                navigateToDetails = {
                    navController.navigate("${TransactionDetailsDestination.route}/${it}")
                },
                navigateToSettings = { navController.navigate(SettingDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToDashboard = { navController.navigate(DashboardDestination.route) }
            )
        }
        composable(route = TransactionEntryDestination.route) {
            TransactionEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = TransactionDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(TransactionDetailsDestination.IDARG) {
                type = NavType.IntType
            })
            ) {
            TransactionDetailsScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = SettingDestination.route) {
            SettingsScreen(
                navigateToSettings = { navController.navigate(SettingDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToDashboard = { navController.navigate(DashboardDestination.route) },
                navigateToCategoryList = { navController.navigate(CategoryListDestination.route) }
            )
        }
        composable(route = DashboardDestination.route) {
            DashboardScreen(
                navigateToSettings = { navController.navigate(SettingDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToDashboard = { navController.navigate(DashboardDestination.route) })
        }
        composable(route = CategoryListDestination.route) {
            CategoryListScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navigateToEntry = { navController.navigate(CategoryEntryDestination.route) },
                navigateToDetail = {
                    navController.navigate("${CategoryDetailDestination.route}/${it}")
                }
            )
        }
        composable(route = CategoryEntryDestination.route) {
            CategoryEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = CategoryDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(CategoryDetailDestination.IDARG) {
                type = NavType.IntType
            })
        ) {
            CategoryDetailScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}