package com.rkeru.expensesapp.ui.settings.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import com.rkeru.expensesapp.ui.theme.ExpensesAppTheme

object CategoryListDestination : NavigationDestination {
    override val route: String = "category_list"
    override val titleRes: Int = R.string.category_list_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    onNavigateUp: () -> Unit,
    navigateToEntry: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: CategoryListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val categoryListUiState by viewModel.categoryListUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = CategoryListDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_category)
                )
            }
        }
    ) { innerPadding ->
        CategoryListBody(
            categoryList = categoryListUiState.categoryList,
            onItemClick = navigateToDetail,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun CategoryListBody(
    categoryList: List<Category>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CategoryList(
            itemList = categoryList,
            onItemClick = { onItemClick(it.id) },
            contentPadding = contentPadding,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
private fun CategoryList(
    itemList: List<Category>,
    onItemClick: (Category) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items ( items = itemList, key = { it.id } ) {item ->
            CategoryItem(
                category = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text (
                text = category.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryListBodyPreview() {
    ExpensesAppTheme {
        CategoryListBody(
            categoryList = listOf(
                Category(1, "Home", "Home Expenses"),
                Category(2, "Sport", "Expenses Related to Sport Activities")
            ),
            onItemClick = {}
        )
    }
}