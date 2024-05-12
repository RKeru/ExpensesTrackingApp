package com.rkeru.expensesapp.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rkeru.expensesapp.ExpensesBottomNavigation
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import com.rkeru.expensesapp.ui.theme.ExpensesAppTheme

object SettingDestination : NavigationDestination {
    override val route: String = "Settings"
    override val titleRes: Int = R.string.settings_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateToSettings: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToDashboard: () -> Unit,
    navigateToCategoryList: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = R.string.settings_screen),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            ExpensesBottomNavigation(
                navigateToSettings = navigateToSettings,
                navigateToHome = navigateToHome,
                navigateToDashboard = navigateToDashboard,
                initialState = 0
            )
        }
    ) { innerPadding ->
        SettingsBody(
            navigateToCategoryList = navigateToCategoryList,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun SettingsBody(
    navigateToCategoryList: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(contentPadding)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .clickable { navigateToCategoryList() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.setting_screen_category),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .clickable { },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.setting_screen_source),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsBodyPreview() {
    ExpensesAppTheme {
        SettingsBody(
            navigateToCategoryList = {  }
        )
    }
}