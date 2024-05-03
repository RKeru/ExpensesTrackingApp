package com.rkeru.expensesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rkeru.expensesapp.ui.home.HomeScreen


object HomeDestination: NavigationDestination {
    override val route: String = "Home"
    override val titleRes: Int = 0  // TODO Replace
}

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
                navigateToDetails = { /*TODO*/ }
            )
        }
    }
}