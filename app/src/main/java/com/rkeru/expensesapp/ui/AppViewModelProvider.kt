package com.rkeru.expensesapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rkeru.expensesapp.ExpensesApplication
import com.rkeru.expensesapp.ui.home.HomeViewModel
import com.rkeru.expensesapp.ui.settings.category.CategoryDetailViewModel
import com.rkeru.expensesapp.ui.settings.category.CategoryEntryViewModel
import com.rkeru.expensesapp.ui.settings.category.CategoryListViewModel
import com.rkeru.expensesapp.ui.settings.source.SourceDetailViewModel
import com.rkeru.expensesapp.ui.settings.source.SourceEntryViewModel
import com.rkeru.expensesapp.ui.settings.source.SourceListViewModel
import com.rkeru.expensesapp.ui.transaction.TransactionDetailsViewModel
import com.rkeru.expensesapp.ui.transaction.TransactionEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(expensesApplication().container.transactionRepo)
        }
        // Initializer for ItemEntryViewModel
        initializer {
            TransactionEntryViewModel(
                expensesApplication().container.transactionRepo,
                expensesApplication().container.categoryRepo,
                expensesApplication().container.sourceRepo
            )
        }
        // Initializer for TransactionDetailsViewModel
        initializer {
            TransactionDetailsViewModel(
                this.createSavedStateHandle(),
                expensesApplication().container.transactionRepo,
                expensesApplication().container.categoryRepo,
                expensesApplication().container.sourceRepo
            )
        }
        // Initializer for CategoryListViewModel
        initializer {
            CategoryListViewModel(expensesApplication().container.categoryRepo)
        }
        // Initializer for CategoryEntryViewModel
        initializer {
            CategoryEntryViewModel(expensesApplication().container.categoryRepo)
        }
        // Initializer for CategoryDetailViewModel
        initializer {
            CategoryDetailViewModel(
                this.createSavedStateHandle(),
                expensesApplication().container.categoryRepo
            )
        }
        // Initializer for SourceListViewModel
        initializer {
            SourceListViewModel(expensesApplication().container.sourceRepo)
        }
        // Initializer for SourceEntryViewModel
        initializer {
            SourceEntryViewModel(expensesApplication().container.sourceRepo)
        }
        // Initializer for SourceDetailViewModel
        initializer {
            SourceDetailViewModel(
                this.createSavedStateHandle(),
                expensesApplication().container.sourceRepo
            )
        }
    }
}

fun CreationExtras.expensesApplication(): ExpensesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ExpensesApplication)