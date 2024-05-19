package com.rkeru.expensesapp.ui.settings.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkeru.expensesapp.data.repository.CategoryRepo
import com.rkeru.expensesapp.ui.transaction.CategoryUiList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CategoryDetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val categoryRepository: CategoryRepo
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private var _categoryUiState by mutableStateOf(CategoryUiState())

    private val emptyCategory: CategoryUiDetail = CategoryUiDetail()

    private val categoryId: Int =
        checkNotNull(savedStateHandle[CategoryDetailDestination.IDARG])

    val categoryUiList: StateFlow<CategoryUiList> =
        categoryRepository.getAllCategoryStream()
            .filterNotNull()
            .map {
                CategoryUiList(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoryUiList()
            )

    val categoryUiState: StateFlow<CategoryUiState> =
        categoryRepository.getCategoryStream(categoryId)
            .filterNotNull()
            .map {
                CategoryUiState(
                    categoryUiDetail = it.toCategoryUiDetail(),
                    isEntryValid = true
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CategoryUiState()
            )

    fun updateUiState(categoryUiDetail: CategoryUiDetail) {
        // First Update -> the internal state is set to the DB value
        if (_categoryUiState.categoryUiDetail == emptyCategory) {
            _categoryUiState = CategoryUiState(
                categoryUiDetail = categoryUiState.value.categoryUiDetail,
                isEntryValid = categoryUiState.value.isEntryValid
            )
        }

        // Check for singles fields
        if (categoryUiDetail.name != categoryUiState.value.categoryUiDetail.name) {
            _categoryUiState = CategoryUiState(
                categoryUiDetail = _categoryUiState.categoryUiDetail.copy(
                    name = categoryUiDetail.name
                ),
                isEntryValid = validateInput()
            )
        }
        if (categoryUiDetail.description != categoryUiState.value.categoryUiDetail.description) {
            _categoryUiState = CategoryUiState(
                categoryUiDetail = _categoryUiState.categoryUiDetail.copy(
                    description = categoryUiDetail.description
                ),
                isEntryValid = validateInput()
            )
        }
    }

    private fun validateInput(
        uiState: CategoryUiDetail = _categoryUiState.categoryUiDetail
    ) : Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun updateCategory() {
        if (validateInput()) {
            categoryRepository.updateCategory(
                _categoryUiState.categoryUiDetail.toCategory()
            )
        }
    }

    suspend fun deleteCategory() {
        // If the user didn't modify the fields, set the internal state as the one from the DB
        if (_categoryUiState.categoryUiDetail == emptyCategory) {
            _categoryUiState = CategoryUiState(
                categoryUiDetail = categoryUiState.value.categoryUiDetail,
                isEntryValid = categoryUiState.value.isEntryValid
            )
        }

        categoryRepository.deleteCategory(
            _categoryUiState.categoryUiDetail.toCategory()
        )
    }
}