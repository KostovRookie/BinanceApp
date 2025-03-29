package com.kostov.binancetestapp.presentation.state

import com.kostov.binancetestapp.model.local.BinanceEntity

data class BinanceUiState(
    val trades: List<BinanceEntity> = emptyList(),
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val isOnline: Boolean = true
)