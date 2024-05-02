package com.rkeru.expensesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkeru.expensesapp.data.model.Transaction
import com.rkeru.expensesapp.data.repository.TransactionRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel (transactionRepository: TransactionRepo) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> =
        transactionRepository.getAllTransactionsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = HomeUiState()
            )
}

data class HomeUiState(val transactionList: List<Transaction> = listOf())