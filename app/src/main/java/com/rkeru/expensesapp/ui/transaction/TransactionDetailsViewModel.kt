package com.rkeru.expensesapp.ui.transaction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventStart
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkeru.expensesapp.data.repository.CategoryRepo
import com.rkeru.expensesapp.data.repository.SourceRepo
import com.rkeru.expensesapp.data.repository.TransactionRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val transactionRepository : TransactionRepo,
    categoryRepository: CategoryRepo,
    sourceRepository: SourceRepo
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private var _transactionUiState by  mutableStateOf(TransactionUiState())

    fun getMutableTransactionDetails(): TransactionUiDetails {
        return _transactionUiState.transactionDetailed.copy()
    }

    private val emptyTransactionUiDetails: TransactionUiDetails = TransactionUiDetails()

    private val transactionId: Int =
        checkNotNull(savedStateHandle[TransactionDetailsDestination.IDARG])

    val transactionUiState: StateFlow<TransactionUiState> =
        transactionRepository.getDetailedExpenseStream(transactionId)
            .filterNotNull()
            .map {
            TransactionUiState(
                transactionDetailed = it.toTransactionUiDetails(),
                isEntryValid = true
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TransactionUiState()
        )

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
        // First Update -> the internal state is set at the DB value
        if (_transactionUiState.transactionDetailed == emptyTransactionUiDetails) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = transactionUiState.value.transactionDetailed,
                isEntryValid = transactionUiState.value.isEntryValid
            )
        }

        // Check of singles fields
        if (transactionDetailed.title != transactionUiState.value.transactionDetailed.title) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    title = transactionDetailed.title
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.isExpense != transactionUiState.value.transactionDetailed.isExpense) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    isExpense = transactionDetailed.isExpense
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.quantity != transactionUiState.value.transactionDetailed.quantity) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    quantity = transactionDetailed.quantity
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.note != transactionUiState.value.transactionDetailed.note) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    note = transactionDetailed.note
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.sourceId != transactionUiState.value.transactionDetailed.sourceId) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    sourceId = transactionDetailed.sourceId
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.sourceName != transactionUiState.value.transactionDetailed.sourceName) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    sourceName = transactionDetailed.sourceName
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.categoryId != transactionUiState.value.transactionDetailed.categoryId) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    categoryId = transactionDetailed.categoryId
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.categoryName != transactionUiState.value.transactionDetailed.categoryName) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    categoryName = transactionDetailed.categoryName
                ),
                isEntryValid = validateInput()
            )
        }
        if (transactionDetailed.date != transactionUiState.value.transactionDetailed.date) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = _transactionUiState.transactionDetailed.copy(
                    date = transactionDetailed.date
                ),
                isEntryValid = validateInput()
            )
        }

    }


    private fun validateInput(
        uiState: TransactionUiDetails = _transactionUiState.transactionDetailed
    ): Boolean {
        return with(uiState) {
            title.isNotBlank() && quantity.isNotBlank()
        }
    }

    suspend fun updateTransaction() {
        Log.d("MyApp", "Updating: ${_transactionUiState.transactionDetailed}")
        if (validateInput()) {
            transactionRepository.updateTransaction(
                _transactionUiState.transactionDetailed.toTransaction()
            )
        }
    }

    suspend fun deleteTransaction() {
        // If the user didn't modify the fields, set the internal state as the one from the DB
        if (_transactionUiState.transactionDetailed == emptyTransactionUiDetails) {
            _transactionUiState = TransactionUiState(
                transactionDetailed = transactionUiState.value.transactionDetailed,
                isEntryValid = transactionUiState.value.isEntryValid
            )
        }

        transactionRepository.deleteTransaction(
            _transactionUiState.transactionDetailed.toTransaction()
        )
    }

}