package com.rkeru.expensesapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rkeru.expensesapp.ExpensesApplication
import com.rkeru.expensesapp.ui.home.HomeViewModel
import com.rkeru.expensesapp.ui.transaction.TransactionEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(expensesApplication().container.transactionRepo)
        }
        // Initializer for ItemEntryViewModel
        initializer {
            TransactionEntryViewModel(expensesApplication().container.transactionRepo)
        }
    }
}

fun CreationExtras.expensesApplication(): ExpensesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ExpensesApplication)