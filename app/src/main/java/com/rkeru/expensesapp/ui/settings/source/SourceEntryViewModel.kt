package com.rkeru.expensesapp.ui.settings.source

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rkeru.expensesapp.data.model.Source
import com.rkeru.expensesapp.data.repository.SourceRepo

class SourceEntryViewModel(private val sourceRepository: SourceRepo) : ViewModel() {

    var sourceUiState by mutableStateOf(SourceUiState())
        private set

    fun updateUiState(sourceUiDetail: SourceUiDetail) {
        sourceUiState = SourceUiState(
            sourceUiDetail = sourceUiDetail,
            isEntryValid = validateInput(sourceUiDetail)
        )
    }

    private fun validateInput(
        uiState: SourceUiDetail = sourceUiState.sourceUiDetail
    ) : Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun saveSource() {
        if (validateInput()) {
            sourceRepository.insertSource(
                sourceUiState.sourceUiDetail.toSource()
            )
        }
    }
}

data class SourceUiState(
    val sourceUiDetail: SourceUiDetail = SourceUiDetail(),
    val isEntryValid: Boolean = false
)

data class SourceUiDetail(
    val id: Int = 0,
    val name: String = "",
    val initialBalance: String = ""
)

fun SourceUiDetail.toSource() : Source = Source(
    id = id,
    name = name,
    initialBalance = initialBalance.toDoubleOrNull() ?: 0.0
)

fun SourceUiDetail.toSourceUiState(
    isEntryValid: Boolean = false
) : SourceUiState = SourceUiState(
    sourceUiDetail = this,
    isEntryValid = isEntryValid
)

fun Source.toSourceUiDetail() : SourceUiDetail = SourceUiDetail(
    id = id,
    name = name,
    initialBalance = initialBalance.toString()
)