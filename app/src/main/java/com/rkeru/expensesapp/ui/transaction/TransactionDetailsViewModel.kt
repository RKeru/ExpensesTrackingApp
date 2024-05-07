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
        private set

    private val transactionId: Int =
        checkNotNull(savedStateHandle[TransactionDetailsDestination.IDARG])

    val transactionUiState: StateFlow<TransactionUiState> =
        transactionRepository.getDetailedExpenseStream(transactionId).map {
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
        _transactionUiState =
            TransactionUiState(
                transactionDetailed = transactionDetailed,
                isEntryValid = validateInput(transactionDetailed)
            )
    }


    private fun validateInput(
        uiState: TransactionUiDetails = _transactionUiState.transactionDetailed
    ): Boolean {
        return with(uiState) {
            title.isNotBlank() && quantity.isNotBlank()
        }
    }

    suspend fun updateTransaction() {
        if (validateInput()) {
            transactionRepository.updateTransaction(
                _transactionUiState.transactionDetailed.toTransaction()
            )
        }
    }

    suspend fun deleteTransaction() {
        transactionRepository.deleteTransaction(
            _transactionUiState.transactionDetailed.toTransaction()
        )
    }

}