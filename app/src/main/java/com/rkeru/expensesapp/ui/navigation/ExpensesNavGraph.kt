package com.rkeru.expensesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rkeru.expensesapp.ui.home.HomeDestination
import com.rkeru.expensesapp.ui.home.HomeScreen
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
                navigateToDetails = { /*TODO*/ },
                navigateToSettings = { /*TODO*/ },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToDashboard = { /*TODO*/ }
            )
        }
        composable(route = TransactionEntryDestination.route) {
            TransactionEntryScreen(
                navigateBack = { navController.navigate(HomeDestination.route) },
                onNavigateUp = { navController.navigate(HomeDestination.route) }
            )
        }
    }
}