package com.rkeru.expensesapp.ui.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkeru.expensesapp.R
import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.data.model.Source
import com.rkeru.expensesapp.data.model.Transaction
import com.rkeru.expensesapp.data.repository.CategoryRepo
import com.rkeru.expensesapp.data.repository.SourceRepo
import com.rkeru.expensesapp.data.repository.TransactionRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class TransactionEntryViewModel (
    private val transactionRepository : TransactionRepo,
    private val categoryRepository: CategoryRepo,
    private val sourceRepository: SourceRepo
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    var transactionUiState by  mutableStateOf(TransactionUiState())
        private set

    val categoryList: StateFlow<CategoryUiList> =
        categoryRepository.getAllCategoryStream().map { CategoryUiList(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoryUiList()
            )

    val sourceList: StateFlow<SourceUiList> =
        sourceRepository.getAllSourcesStream().map { SourceUiList(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SourceUiList()
            )

    fun updateUiState(transactionDetailed: TransactionUiDetails) {
        transactionUiState =
            TransactionUiState(
                transactionDetailed = transactionDetailed,
                isEntryValid = validateInput(transactionDetailed)
            )
    }

    private fun validateInput(
        uiState: TransactionUiDetails = transactionUiState.transactionDetailed
    ): Boolean {
        return with(uiState) {
            title.isNotBlank() && quantity.isNotBlank()
        }
    }

    suspend fun saveTransaction() {
        if (validateInput()) {
            transactionRepository.insertTransaction(
                transactionUiState.transactionDetailed.toTransaction()
            )
        }
    }
}


data class CategoryUiList(val categoryList: List<Category> = listOf())

data class SourceUiList(val sourceList: List<Source> = listOf())

data class TransactionUiState(
    val transactionDetailed: TransactionUiDetails = TransactionUiDetails(),
    val isEntryValid: Boolean = false
)

data class TransactionUiDetails(
    val transactionId: Int = 0,
    val title: String = "",
    val isExpense: Boolean = true,
    val quantity: String = "",
    val note: String = "",
    val sourceId: Int = 1,
    val sourceName: String = "",
    val categoryId: Int = 1,
    val categoryName: String = "",
    val date: String = ""
)

fun TransactionUiDetails.toTransaction() : Transaction = Transaction(
    id = transactionId,
    title = title,
    isExpense = isExpense,
    quantity = quantity.toDoubleOrNull() ?: 0.0,
    note = note,
    sourceId = sourceId,
    categoryId = categoryId,
    date = if (date != "") {
        Date(
            LocalDate.parse(
                date, DateTimeFormatter.ofPattern("dd/MM/yyyy")
            ).atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
        )
    } else {
        Date()
    }
)
