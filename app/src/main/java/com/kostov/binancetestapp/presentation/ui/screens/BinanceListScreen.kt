package com.kostov.binancetestapp.presentation.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kostov.binancetestapp.presentation.state.BinanceUiState
import com.kostov.binancetestapp.presentation.ui.composables.CryptoTradesItem
import com.kostov.binancetestapp.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinanceListScreen(
    state: BinanceUiState,
    onRefresh: () -> Unit,
    onInit: () -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        onInit()
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(Constants.TITLE_BINANCE_MARKET)
                    }
                )

                TextField(
                    value = state.searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text(Constants.PLACEHOLDER_TEXT_SEARCH) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    singleLine = true
                )

                val statusText = if (state.isOnline) {
                    "Live data from internet"
                } else {
                    "Offline mode — showing cached data"
                }

                Text(
                    text = statusText,
                    color = if (state.isOnline) Color(0xFF4CAF50) else Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }
        }
    ) { innerPadding ->

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val filteredTrades = remember(state.searchQuery, state.trades) {
                if (state.searchQuery.isBlank()) state.trades
                else state.trades.filter {
                    it.symbol.contains(state.searchQuery.trim(), ignoreCase = true)
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredTrades) { trade ->
                    CryptoTradesItem(trade)
                }
            }

            if (filteredTrades.isEmpty() && !state.isRefreshing) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(Constants.TEXT_NO_MATCHES)
                }
            }
        }
    }
}