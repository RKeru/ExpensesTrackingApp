package com.rkeru.expensesapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rkeru.expensesapp.ui.navigation.ExpensesNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun ExpensesApp(navController: NavHostController = rememberNavController()) {
    ExpensesNavHost(navController = navController)
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                       imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"  // TODO add to string res
                    )
                }
            }
        }
    )
}

/**
 * App Bottom Navigation Bar
 */
@Composable
fun ExpensesBottomAppBar() {
    // TODO Implement
}