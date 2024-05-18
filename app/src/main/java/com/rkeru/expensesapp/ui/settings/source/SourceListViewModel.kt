package com.rkeru.expensesapp.ui.settings.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkeru.expensesapp.data.model.Source
import com.rkeru.expensesapp.data.repository.SourceRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SourceListViewModel (private val sourceRepository: SourceRepo) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    val sourceListUiState: StateFlow<SourceListUiState> =
        sourceRepository.getAllSourcesStream().map { SourceListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = SourceListUiState()
            )
}

data class SourceListUiState(val sourceList: List<Source> = listOf())