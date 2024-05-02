package com.rkeru.expensesapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.data.model.Transaction
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.theme.ExpensesAppTheme
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HomeScreen(
    navigateToEntry: () -> Unit,
    navigateToDetails: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
}

@Composable
private fun HomeBody(
    transactionList: List<Transaction>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TransactionList(
            itemList = transactionList,
            onItemClick = { onItemClick(it.id) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
private fun TransactionList(
    itemList: List<Transaction>,
    onItemClick: (Transaction) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id } ) {item ->
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
    transaction: Transaction,
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

            Text(
                text = stringResource(R.string.transaction_qty, transaction.quantity),
                style = MaterialTheme.typography.titleMedium,
                color = if (transaction.isExpense) Color.Red else Color.Green
            )
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
                Transaction(1, "Spesa 1", true, 100.5, "", 1, 1, Date()),
                Transaction(2, "Spesa 2", true, 52.6, "", 1, 3, Date()),
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
            transaction = Transaction(1, "Abbonamento Netflix", true, 12.99, "", 1, 1, Date())
        )
    }
}