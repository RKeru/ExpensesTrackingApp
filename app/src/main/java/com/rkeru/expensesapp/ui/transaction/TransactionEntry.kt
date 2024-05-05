package com.rkeru.expensesapp.ui.transaction

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rkeru.expensesapp.ExpensesTopAppBar
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.data.model.Source
import com.rkeru.expensesapp.toBool
import com.rkeru.expensesapp.ui.AppViewModelProvider
import com.rkeru.expensesapp.ui.navigation.NavigationDestination
import com.rkeru.expensesapp.ui.theme.ExpensesAppTheme
import java.util.Currency
import java.util.Locale

object TransactionEntryDestination: NavigationDestination {
    override val route: String = "item_entry"
    override val titleRes: Int = R.string.add_item
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToDashboard: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: TransactionEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ExpensesTopAppBar(
                title = stringResource(id = TransactionEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        // TODO Add ItemEntryBody
    }
}

@Composable
fun ItemEntryBody(
    transactionUiState: TransactionUiState,
    categoryList: List<Category>,
    sourceList: List<Source>,
    onItemValueChange: (TransactionUiDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        TransactionInputForm(
            transactionUiDetails = transactionUiState.transactionDetailed,
            categoryList = categoryList,
            sourceList = sourceList,
            onValueChange = onItemValueChange
        )
        // TODO SaveButton
    }
}

@Composable
fun TransactionInputForm(
    transactionUiDetails: TransactionUiDetails,
    categoryList: List<Category>,
    sourceList: List<Source>,
    modifier: Modifier = Modifier,
    onValueChange: (TransactionUiDetails) -> Unit,
    enabled: Boolean = true
) {
    var expenseType by remember { mutableStateOf(0) }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = transactionUiDetails.title,
            onValueChange =  { onValueChange(transactionUiDetails.copy(title = it)) },
            label = { Text(text = stringResource(id = R.string.entry_screen_title)) },
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
            value = transactionUiDetails.quantity,
            onValueChange = { onValueChange(transactionUiDetails.copy(quantity = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(id = R.string.entry_screen_value)) },
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )
        TextSwitch(
            selectedIndex = expenseType,
            items = listOf(
                stringResource(id = R.string.entry_screen_type_expense),
                stringResource(id = R.string.entry_screen_type_income)
            ),
            onSelectionChange = {
                expenseType = it
                onValueChange(transactionUiDetails.copy(isExpense = expenseType.toBool))
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryDropDownMenu(
                title = stringResource(id = R.string.entry_screen_category),
                categoryList = categoryList,
                transactionUiDetails = transactionUiDetails,
                onValueChange = onValueChange
            )
            Spacer(modifier = Modifier.weight(1f))
            SourceDropDownMenu(
                title = stringResource(id = R.string.entry_screen_source),
                sourceList = sourceList,
                transactionUiDetails = transactionUiDetails,
                onValueChange = onValueChange
            )
        }
        OutlinedTextField(
            value = transactionUiDetails.note,
            onValueChange = { onValueChange(transactionUiDetails.copy(note = it)) },
            label = { Text(text = stringResource(id = R.string.entry_screen_note)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            enabled = true,
            singleLine = false
        )
    }
}


@Composable
private fun CategoryDropDownMenu(
    title: String,
    categoryList: List<Category>,
    transactionUiDetails: TransactionUiDetails,
    onValueChange: (TransactionUiDetails) -> Unit,
    modifier: Modifier = Modifier,
) {

    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(0) }

    Column (
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .size(width = 100.dp, height = 48.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .background(Color.White)
                .clickable { expanded = true }
        ) {
            Text(
                text = categoryList[selected].name,
                modifier = Modifier
                    .fillMaxSize()
                    //.padding(dimensionResource(id = R.dimen.padding_small))
                    .padding(12.dp)
                    .background(Color.Transparent)

            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.LightGray),
            //offset = DpOffset.Zero,
        ) {
            categoryList.forEachIndexed { index, category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        selected = index
                        onValueChange(
                            transactionUiDetails.copy(
                                categoryId = category.id,
                                categoryName = category.name
                            )
                        )
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
private fun SourceDropDownMenu(
    title: String,
    sourceList: List<Source>,
    transactionUiDetails: TransactionUiDetails,
    onValueChange: (TransactionUiDetails) -> Unit,
    modifier: Modifier = Modifier,
) {

    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(0) }

    Column (
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .size(width = 100.dp, height = 48.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .background(Color.White)
                .clickable { expanded = true }
        ) {
            Text(
                text = sourceList[selected].name,
                modifier = Modifier
                    .fillMaxSize()
                    //.padding(dimensionResource(id = R.dimen.padding_small))
                    .padding(12.dp)
                    .background(Color.Transparent)

            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.LightGray),
            //offset = DpOffset.Zero,
        ) {
            sourceList.forEachIndexed { index, source ->
                DropdownMenuItem(
                    text = { Text(source.name) },
                    onClick = {
                        selected = index
                        onValueChange(
                            transactionUiDetails.copy(
                                sourceId = source.id,
                                sourceName = source.name
                            )
                        )
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onSelectionChange: (Int) -> Unit
) {

    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xfff3f3f2))
            .padding(8.dp)
    ) {
        if (items.isNotEmpty()) {

            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            // This is for shadow layer matching white background
            /*Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .width(tabWidth)
                    .fillMaxHeight()
            )*/

            Row(modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    // This is for setting black tex while drawing on white background
                    val padding = 8.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                        color = Color.Transparent,
                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                    )

                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = if (selectedIndex == 0) {
                                Color.Red.copy(alpha = 0.5f)
                            } else {
                                Color.Green.copy(alpha = 0.5f)
                            },
                            cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                            blendMode = BlendMode.SrcOut
                        )
                    }
                }
            ) {
                items.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            //.background(Color.Transparent)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    onSelectionChange(index)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

private fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionEntryScreenPreview() {
    ExpensesAppTheme {
        ItemEntryBody(
            transactionUiState = TransactionUiState(),
            categoryList = listOf(
                Category(1, "Casa", ""),
                Category(2, "Spesa", ""),
                Category(3, "Sport", "")
            ),
            sourceList = listOf(
                Source(1, "BancaXYZ", 0.0),
                Source(2, "Satispay", 0.0)
            ),
            onItemValueChange = {},
            onSaveClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DropDownMenuPreview(){
    val items = listOf("Element 1", "Element 2")
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(0) }
    Text(
        text = items[selected],
        modifier = Modifier
            .padding(16.dp)
            .clickable { expanded = true }
    )
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        items.forEachIndexed { index, s ->
            DropdownMenuItem(text = { Text(s) }, onClick = {
                selected = index
                expanded = false
            })
        }
    }
}

@Preview
@Composable
private fun TextSwitchTest() {
    val items = remember {
        listOf("Man", "Woman")
    }

    var selectedIndex by remember {
        mutableStateOf(0)
    }


    Column {
        TextSwitch(
            selectedIndex = selectedIndex,
            items = items,
            onSelectionChange = {
                selectedIndex = it
            }
        )
    }
}