package com.rkeru.expensesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rkeru.expensesapp.ui.home.HomeDestination
import com.rkeru.expensesapp.ui.home.HomeScreen


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
                navigateToEntry = { /*TODO*/ },
                navigateToDetails = { /*TODO*/ },
                navigateToSettings = { /*TODO*/ },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToDashboard = { /*TODO*/ }
            )
        }
    }
}