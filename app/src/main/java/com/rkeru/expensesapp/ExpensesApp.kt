package com.rkeru.expensesapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
fun ExpensesBottomNavigation(
    navigateToSettings: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToDashboard: () -> Unit,
    modifier: Modifier = Modifier,
    initialState: Int = 1,
    inverseColor: Boolean = true
) {
    var selectedItem by remember { mutableIntStateOf(initialState) }
    val labels = listOf(
        stringResource(R.string.settings),
        stringResource(R.string.home),
        stringResource(R.string.dashboard)
    )
    val icons = listOf(
        painterResource(R.drawable.settings_icon),
        painterResource(R.drawable.home_icon),
        painterResource(R.drawable.chart_icon)
    )

    BottomNavigation (windowInsets = BottomNavigationDefaults.windowInsets) {
        icons.forEachIndexed { index, icon ->
            val iconSize = 48.dp
            BottomNavigationItem(
                icon = {
                    Icon (
                        painter = icon,
                        contentDescription = labels[index],
                        tint = if (inverseColor) Color.White else Color.Black
                    )
                },
                label = {
                    Text (
                        labels[index],
                        style = MaterialTheme.typography.titleSmall,
                        color = if (inverseColor) Color.White else Color.Black
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    when(selectedItem) {
                        0 -> navigateToSettings()
                        1 -> navigateToHome()
                        2 -> navigateToDashboard()
                    }
                },
                modifier = modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = if(selectedItem == index) {
                            Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha=0.05f),
                                    Color.White.copy(alpha=0.4f)
                                ),
                                center = Offset.Unspecified,
                                radius = 150f
                            )
                        } else {
                            SolidColor(Color.Transparent)
                        }
                    )
                    .padding(top = 5.dp)
            )
        }
    }
}
