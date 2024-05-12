package com.rkeru.expensesapp.ui.settings.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object CategoryEntryDestination: NavigationDestination {
    override val route: String = "category_entry"
    override val titleRes: Int = R.string.add_category_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: CategoryEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = CategoryEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        CategoryEntryBody(
            categoryUiState = viewModel.categoryUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                  coroutineScope.launch {
                      viewModel.saveCategory()
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
private fun CategoryEntryBody(
    categoryUiState: CategoryUiState,
    onItemValueChange: (CategoryUiDetail) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        CategoryInputForm(
            categoryUiDetail = categoryUiState.categoryUiDetail,
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
               Text (text = stringResource(id = R.string.entry_category_screen_cancel))
           }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onSaveClick,
                enabled = categoryUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.entry_category_screen_add))
            }
        }
    }
}

@Composable
fun CategoryInputForm(
    categoryUiDetail: CategoryUiDetail,
    modifier: Modifier = Modifier,
    onValueChange: (CategoryUiDetail) -> Unit,
    enabled: Boolean = true
) {

    val nameTxt = remember { mutableStateOf("") }
    val descriptionTxt = remember { mutableStateOf("") }

    LaunchedEffect(
        key1 = categoryUiDetail.name,
        key2 = categoryUiDetail.description
    ) {
        if (!enabled) {
            nameTxt.value = categoryUiDetail.name
            descriptionTxt.value = categoryUiDetail.description
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = nameTxt.value,
            onValueChange = {
                nameTxt.value = it
                onValueChange(categoryUiDetail.copy(name = nameTxt.value))
            },
            label = { Text(text = stringResource(id = R.string.entry_category_screen_title)) },
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
            value = descriptionTxt.value,
            onValueChange = {
                descriptionTxt.value = it
                onValueChange(categoryUiDetail.copy(description = descriptionTxt.value))
            },
            label = { Text(text = stringResource(id = R.string.entry_category_screen_description)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            enabled = enabled,
            singleLine = false
        )
    }
}