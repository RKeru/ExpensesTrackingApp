package com.rkeru.expensesapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.ExpensesBottomNavigation
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.data.model.TransactionDetails
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import com.rkeru.expensesapp.ui.theme.ExpensesAppTheme
import java.text.SimpleDateFormat
import java.util.Date

object HomeDestination: NavigationDestination {
    override val route: String = "Home"
    override val titleRes: Int = R.string.home_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToEntry: () -> Unit,
    navigateToDetails: (Int) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToDashboard: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            ExpensesBottomNavigation(
                navigateToSettings = navigateToSettings,
                navigateToHome = navigateToHome,
                navigateToDashboard = navigateToDashboard
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
            ) {
               Icon(
                   imageVector = Icons.Default.Add,
                   contentDescription = stringResource(R.string.add_transaction)
               )
            }
        }
    ) { innerPadding ->
        HomeBody(
            transactionList = homeUiState.transactionList,
            onItemClick = navigateToDetails,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding
        )

    }
}

@Composable
private fun HomeBody(
    transactionList: List<TransactionDetails>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(2.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TransactionList(
            itemList = transactionList,
            onItemClick = { onItemClick(it.transactionId) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
private fun Budget(

){

}

@Composable
private fun TransactionList(
    itemList: List<TransactionDetails>,
    onItemClick: (TransactionDetails) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.transactionId } ) {item ->
            TransactionItem(
                transaction = item,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: TransactionDetails,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = transaction.date.toSimpleString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.money_qty, transaction.quantity),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (transaction.isExpense) Color.Red else Color.Green
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = transaction.categoryName,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(this)
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    ExpensesAppTheme {
        HomeBody(
            transactionList = listOf(
                TransactionDetails(
                    1, "Spesa 1", true, 100.5, "",
                    1, "Banca XYZ",1, "Casa", Date()),
                TransactionDetails(
                    2, "Spesa 2", true, 52.6, "",
                    1, "Banca XYZ", 2, "Alimentari", Date()),
            ),
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    ExpensesAppTheme {
        TransactionItem(
            transaction = TransactionDetails(
                1, "Abbonamento Netflix", true, 12.99, "",
                1, "Banca XYZ", 2, "Casa", Date())
        )
    }
}