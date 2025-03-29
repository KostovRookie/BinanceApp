package com.kostov.binancetestapp.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kostov.binancetestapp.R
import com.kostov.binancetestapp.model.local.BinanceEntity
import com.kostov.binancetestapp.utils.getCryptoIconUrl
import com.kostov.binancetestapp.utils.splitBinanceSymbol

@Composable
fun CryptoTradesItem(trades: BinanceEntity) {
    val (baseSymbol, quoteSymbol) = splitBinanceSymbol(trades.symbol)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf(baseSymbol, quoteSymbol).forEach { symbol ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = getCryptoIconUrl(symbol),
                                placeholder = painterResource(id = R.drawable.ic_crypto_placeholder),
                                error = painterResource(id = R.drawable.ic_crypto_placeholder)
                            ),
                            contentDescription = symbol,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "$baseSymbol / $quoteSymbol",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Bid: ${trades.bidPrice} | Ask: ${trades.askPrice}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    val changeColor =
                        if (trades.priceChangePercent.contains("-")) Color.Red else Color(0xFF4CAF50)
                    Text(
                        text = "${trades.priceChangePercent}%",
                        color = changeColor,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}