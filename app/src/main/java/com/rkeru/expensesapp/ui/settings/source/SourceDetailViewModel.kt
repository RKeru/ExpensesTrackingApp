package com.rkeru.expensesapp.ui.settings.source

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkeru.expensesapp.data.repository.SourceRepo
import com.rkeru.expensesapp.ui.transaction.SourceUiList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SourceDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val sourceRepository: SourceRepo
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    private var _sourceUiState by mutableStateOf(SourceUiState())

    private val emptySource: SourceUiDetail = SourceUiDetail()

    private val sourceId: Int =
        checkNotNull(savedStateHandle[SourceDetailDestination.IDARG])

    val sourceUiList: StateFlow<SourceUiList> =
        sourceRepository.getAllSourcesStream()
            .filterNotNull()
            .map {
                SourceUiList(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = SourceUiList()
            )

    val sourceUiState: StateFlow<SourceUiState> =
        sourceRepository.getSourceStream(sourceId)
            .filterNotNull()
            .map {
                SourceUiState(
                    sourceUiDetail = it.toSourceUiDetail(),
                    isEntryValid = true
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = SourceUiState()
            )

    fun updateUiState(sourceUiDetail: SourceUiDetail) {
        // First Update -> the internal state is set to the DB value
        if(_sourceUiState.sourceUiDetail == emptySource) {
            _sourceUiState = SourceUiState(
                sourceUiDetail = sourceUiState.value.sourceUiDetail,
                isEntryValid = sourceUiState.value.isEntryValid
            )
        }

        // Check for singles fields
        if (sourceUiDetail.name != sourceUiState.value.sourceUiDetail.name) {
            _sourceUiState = SourceUiState(
                sourceUiDetail = _sourceUiState.sourceUiDetail.copy(
                    name = sourceUiDetail.name
                ),
                isEntryValid = validateInput()
            )
        }
    }

    private fun validateInput(
        uiState: SourceUiDetail = _sourceUiState.sourceUiDetail
    ): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun updateSource() {
        if (validateInput()) {
            sourceRepository.updateSource(
                _sourceUiState.sourceUiDetail.toSource()
            )
        }
    }

    suspend fun deleteCategory() {
        // If the user didn't modify the fields, set the internal state as the one from the DB
        if(_sourceUiState.sourceUiDetail == emptySource) {
            _sourceUiState = SourceUiState(
                sourceUiDetail = sourceUiState.value.sourceUiDetail,
                isEntryValid = sourceUiState.value.isEntryValid
            )
        }

        sourceRepository.deleteSource(
            _sourceUiState.sourceUiDetail.toSource()
        )
    }
}