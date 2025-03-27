package com.kostov.binancetestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostov.binancetestapp.model.BinanceRepository
import com.kostov.binancetestapp.presentation.state.BinanceUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BinanceViewModel(private val repository: BinanceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(BinanceUiState())
    val uiState: StateFlow<BinanceUiState> = _uiState

    fun loadTrades() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            val result = repository.fetchAndCacheCryptoTrades()
            _uiState.update {
                it.copy(
                    trades = result,
                    isRefreshing = false
                )
            }
        }
    }
}