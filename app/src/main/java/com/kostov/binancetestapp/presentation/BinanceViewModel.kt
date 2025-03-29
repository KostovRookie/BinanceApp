package com.kostov.binancetestapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostov.binancetestapp.model.BinanceRepository
import com.kostov.binancetestapp.presentation.state.BinanceUiState
import com.kostov.binancetestapp.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BinanceViewModel(
    private val repository: BinanceRepository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(BinanceUiState())
    val uiState: StateFlow<BinanceUiState> = _uiState

    val isDataLoaded: StateFlow<Boolean> = uiState
        .map { it.trades.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun loadTrades() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            val online = NetworkUtils.isNetworkAvailable(context)

            val result = if (online) {
                try {
                    repository.fetchAndCacheCryptoTrades()
                } catch (e: Exception) {
                    repository.getCachedTrades()
                }
            } else {
                repository.getCachedTrades()
            }

            _uiState.update {
                it.copy(
                    trades = result,
                    isRefreshing = false,
                    isOnline = online
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun clearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
    }
}