package com.example.fetchtakehomeproject.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchtakehomeproject.domain.GetAllSortedItemsUseCase
import com.example.fetchtakehomeproject.domain.model.DomainItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getAllSortedItemsUseCase: GetAllSortedItemsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState
        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            MainUiState()
        )

    private suspend fun loadData() {
        val data = getAllSortedItemsUseCase()
        _uiState.update { it.copy(isLoading = false, data = data, isError = data.isEmpty()) }
    }
}

data class MainUiState(
    val isLoading: Boolean = true,
    val data: List<DomainItem> = emptyList(),
    val isError: Boolean = false
)