package com.kostov.binancetestapp.presentation.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kostov.binancetestapp.R
import com.kostov.binancetestapp.model.local.BinanceEntity
import com.kostov.binancetestapp.presentation.state.BinanceUiState
import com.kostov.binancetestapp.utils.getCryptoIconUrl
import com.kostov.binancetestapp.utils.splitBinanceSymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinanceListScreen(
    state: BinanceUiState,
    onRefresh: () -> Unit,
    onInit: () -> Unit
) {
    LaunchedEffect(Unit) {
        onInit()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Binance Prices") }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.trades) { ticker ->
                    CryptoTradesItem(ticker)
                }
            }

            if (state.trades.isEmpty() && !state.isRefreshing) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No data loaded.")
                }
            }
        }
    }
}

@Composable
fun CryptoTradesItem(trades: BinanceEntity) {
    val (baseSymbol, quoteSymbol) = splitBinanceSymbol(trades.symbol)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf(baseSymbol, quoteSymbol).forEach { symbol ->
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = getCryptoIconUrl(symbol),
                            placeholder = painterResource(id = R.drawable.ic_crypto_placeholder),
                            error = painterResource(id = R.drawable.ic_crypto_placeholder)
                        ),
                        contentDescription = symbol,
                        modifier = Modifier.size(28.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Column {
                Text(
                    text = "${trades.symbol} (${trades.priceChangePercent}%)",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Bid / Ask: ${trades.bidPrice} / ${trades.askPrice}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
