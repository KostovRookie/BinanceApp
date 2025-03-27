package com.kostov.binancetestapp.presentation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.kostov.binancetestapp.presentation.BinanceViewModel
import com.kostov.binancetestapp.presentation.ui.BinanceListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun BinanceListRoute(viewModel: BinanceViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    BinanceListScreen(
        state = uiState.value,
        onRefresh = { viewModel.loadTrades() },
        onInit = { viewModel.loadTrades() }
    )
}