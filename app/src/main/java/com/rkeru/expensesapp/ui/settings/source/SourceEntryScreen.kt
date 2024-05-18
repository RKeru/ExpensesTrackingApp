package com.rkeru.expensesapp.ui.settings.source

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object SourceEntryDestination: NavigationDestination {
    override val route: String = "source_entry"
    override val titleRes: Int = R.string.add_source_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: SourceEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = SourceEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        SourceEntryBody(
            sourceUiState = viewModel.sourceUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveSource()
                    navigateBack()
                }
            },
            onCancelClick = { navigateBack() },
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
private fun SourceEntryBody(
    sourceUiState: SourceUiState,
    onItemValueChange: (SourceUiDetail) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        SourceInputForm(
            sourceUiDetail = sourceUiState.sourceUiDetail,
            onValueChange = onItemValueChange
        )
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onCancelClick,
                enabled = true,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.weight(1f)
            ) {
                Text (text = stringResource(id = R.string.entry_source_screen_cancel))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onSaveClick,
                enabled = sourceUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.entry_source_screen_add))
            }
        }
    }
}

@Composable
fun SourceInputForm(
    sourceUiDetail: SourceUiDetail,
    modifier: Modifier = Modifier,
    onValueChange: (SourceUiDetail) -> Unit,
    enabled: Boolean = true,
    canModifyBalance: Boolean = true
) {

    val nameTxt = remember { mutableStateOf("") }
    val initialBalance = remember { mutableStateOf("") }

    LaunchedEffect(
        key1 = sourceUiDetail.name,
        key2 = sourceUiDetail.currentBalance
    ) {
        if (!enabled) {
            nameTxt.value = sourceUiDetail.name
            initialBalance.value = sourceUiDetail.currentBalance
        }
    }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = nameTxt.value,
            onValueChange = {
                nameTxt.value = it
                onValueChange(sourceUiDetail.copy(name = nameTxt.value))
            },
            label = { Text(text = stringResource(id = R.string.entry_source_screen_title)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = initialBalance.value,
            onValueChange = {
                initialBalance.value = it
                onValueChange(sourceUiDetail.copy(currentBalance = initialBalance.value))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(text = stringResource(id = R.string.entry_source_screen_balance)) },
            leadingIcon = { Text(Currency.getInstance(Locale.ITALY).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = canModifyBalance,
            singleLine = true
        )
    }

}