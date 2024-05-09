package com.rkeru.expensesapp.ui.transaction

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.data.model.Source
import com.rkeru.expensesapp.toInt
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import com.rkeru.expensesapp.ui.theme.ExpensesAppTheme
import kotlinx.coroutines.launch

object TransactionDetailsDestination: NavigationDestination {
    override val route: String = "item_details"
    override val titleRes: Int = R.string.transaction_details_screen
    const val IDARG = "transactionId"
    val routeWithArgs = "$route/{$IDARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: TransactionDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val categoryUiList by viewModel.categoryList.collectAsState()
    val sourceUiList by viewModel.sourceList.collectAsState()
    val transactionUiState = viewModel.transactionUiState.collectAsState()

    Scaffold (
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = TransactionDetailsDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        TransactionDetailsBody(
            transactionUiState = transactionUiState.value,
            categoryList = categoryUiList.categoryList,
            sourceList = sourceUiList.sourceList,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTransaction()
                    navigateBack()
                }
            },
            onDeleteClick = {
                coroutineScope.launch {
                    viewModel.deleteTransaction()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }

}

@Composable
fun TransactionDetailsBody (
    transactionUiState: TransactionUiState,
    categoryList: List<Category>,
    sourceList: List <Source>,
    onItemValueChange: (TransactionUiDetails) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val enableModify = remember { mutableStateOf(false) }
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        TransactionInputForm(
            transactionUiDetails = transactionUiState.transactionDetailed,
            categoryList = categoryList,
            sourceList = sourceList,
            onValueChange = onItemValueChange,
            enabled = enableModify.value
        )
        Row ( modifier = Modifier.fillMaxWidth() ) {
            if (enableModify.value) {
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    enabled = true,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = R.string.detail_screen_delete))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onSaveClick,
                    enabled = true,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = R.string.detail_screen_update))
                }
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                    )
                }
            } else {
                Button(
                    onClick = { enableModify.value = true },
                    enabled = true,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1f)
                ) {
                    Text (text = stringResource(id = R.string.detail_screen_modify))
                }
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.detail_screen_attention)) },
        text = { Text(stringResource(R.string.detail_screen_delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.detail_screen_no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.detail_screen_yes))
            }
        })
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailsScreenPreview() {
    ExpensesAppTheme {
        TransactionDetailsBody(
            transactionUiState =  TransactionUiState(),
            categoryList = listOf(
                Category(1, "Casa", ""),
                Category(2, "Spesa", ""),
                Category(3, "Sport", "")
            ),
            sourceList = listOf(
                Source(1, "BancaXYZ", 0.0),
                Source(2, "Satispay", 0.0)
            ),
            onItemValueChange = { },
            onSaveClick = { },
            onDeleteClick = { }
        )
    }
}