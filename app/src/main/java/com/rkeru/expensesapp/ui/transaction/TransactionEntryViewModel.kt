package com.rkeru.expensesapp.ui.transaction

import androidx.lifecycle.ViewModel
import com.rkeru.expensesapp.data.model.TransactionDetails
import com.rkeru.expensesapp.data.repository.TransactionRepo
import java.util.Date

class TransactionEntryViewModel (private val transactionRepository : TransactionRepo) : ViewModel() {
}

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
    val sourceId: Int = 0,
    val sourceName: String = "",
    val categoryId: Int = 0,
    val categoryName: String = "",
    val date: String = ""
)