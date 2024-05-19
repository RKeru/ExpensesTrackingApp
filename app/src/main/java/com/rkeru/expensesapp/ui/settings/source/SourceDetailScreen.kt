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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object SourceDetailDestination: NavigationDestination {
    override val route: String = "source_detail"
    override val titleRes: Int = R.string.source_detail_screen
    const val IDARG = "id"
    val routeWithArgs = "$route/{$IDARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceDetailScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: SourceDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val sourceUiState = viewModel.sourceUiState.collectAsState()
    val sourceUiList = viewModel.sourceUiList.collectAsState()

    Scaffold(
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = SourceDetailDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        SourceDetailBody(
            sourceUiState = sourceUiState.value,
            isLastElement = sourceUiList.value.sourceList.size <= 1,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSource()
                    navigateBack()
                }
            },
            onDeleteClick = {
                coroutineScope.launch {
                    viewModel.deleteCategory()
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
fun SourceDetailBody(
    sourceUiState: SourceUiState,
    isLastElement: Boolean,
    onItemValueChange: (SourceUiDetail) -> Unit,
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
        SourceInputForm(
            sourceUiDetail = sourceUiState.sourceUiDetail,
            onValueChange = onItemValueChange,
            enabled = enableModify.value,
            canModifyBalance = false
        )
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            if (enableModify.value) {
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    enabled = !isLastElement,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1f)
                ) {
                    Text (text = stringResource(id = R.string.detail_source_screen_delete))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onSaveClick,
                    enabled = true,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = R.string.detail_source_screen_update))
                }
                if (deleteConfirmationRequired) {
                    DeleteSourceConfirmationDialog(
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
                    Text (text = stringResource(id = R.string.detail_source_screen_modify))
                }
            }
        }
        if (enableModify.value and isLastElement) {
            Text(text = stringResource(id = R.string.detail_source_screen_last_element))
        }
    }
}

@Composable
private fun DeleteSourceConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.detail_source_screen_attention)) },
        text = { Text(stringResource(R.string.detail_source_screen_delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.detail_source_screen_no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.detail_source_screen_yes))
            }
        })
}