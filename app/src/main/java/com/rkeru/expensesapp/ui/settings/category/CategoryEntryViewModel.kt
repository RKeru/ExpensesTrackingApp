package com.rkeru.expensesapp.ui.settings.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rkeru.expensesapp.data.model.Category
import com.rkeru.expensesapp.data.repository.CategoryRepo

class CategoryEntryViewModel(
    private val categoryRepository : CategoryRepo,
) : ViewModel() {

    var categoryUiState by mutableStateOf(CategoryUiState())
        private set

    fun updateUiState(categoryUiDetail: CategoryUiDetail) {
        categoryUiState = CategoryUiState(
            categoryUiDetail = categoryUiDetail,
            isEntryValid = validateInput(categoryUiDetail)
        )
    }

    private fun validateInput(
        uiState: CategoryUiDetail = categoryUiState.categoryUiDetail
    ) : Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun saveCategory() {
        if (validateInput()) {
            categoryRepository.insertCategory(
                categoryUiState.categoryUiDetail.toCategory()
            )
        }
    }
}

data class CategoryUiState(
    val categoryUiDetail: CategoryUiDetail = CategoryUiDetail(),
    val isEntryValid: Boolean = false
)

data class CategoryUiDetail(
    val id: Int = 0,
    val name: String = "",
    val description: String = ""
)

fun CategoryUiDetail.toCategory() : Category = Category(
    id = id,
    name = name,
    description = description
)

fun CategoryUiDetail.toCategoryUiState(
    isEntryValid: Boolean = false
) : CategoryUiState = CategoryUiState(
    categoryUiDetail = this,
    isEntryValid = isEntryValid
)

fun Category.toCategoryUiDetail() : CategoryUiDetail = CategoryUiDetail(
    id = id,
    name = name,
    description = description
)