package com.rkeru.expensesapp.ui.settings.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.data.repository.CategoryRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CategoryListViewModel (private val categoryRepository: CategoryRepo) : ViewModel(){

    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    val categoryListUiState: StateFlow<CategoryListUiState> =
        categoryRepository.getAllCategoryStream().map { CategoryListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = CategoryListUiState()
            )
}

data class CategoryListUiState(val categoryList: List<Category> = listOf())