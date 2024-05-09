package com.rkeru.expensesapp.ui.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.rkeru.expensesapp.ExpensesBottomNavigation
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.ui.navigation.NavigationDestination

object DashboardDestination : NavigationDestination {
    override val route: String = "Dashboard"
    override val titleRes: Int = R.string.dashboard_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navigateToSettings: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = R.string.dashboard_screen),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            ExpensesBottomNavigation(
                navigateToSettings = navigateToSettings,
                navigateToHome = navigateToHome,
                navigateToDashboard = navigateToDashboard,
                initialState = 2
            )
        }
    ) { innerPadding ->
        Text(
            text = "Dashboard: Coming Soon...",
        )
    }
}