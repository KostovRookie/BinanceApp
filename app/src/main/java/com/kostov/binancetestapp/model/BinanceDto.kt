package com.kostov.binancetestapp.model

import kotlinx.serialization.Serializable

@Serializable
data class BinanceDto(
    val symbol: String,
    val priceChange: String,
    val priceChangePercent: String,
    val bidPrice: String,
    val askPrice: String,
    val weightedAvgPrice: String,
    val prevClosePrice: String,
    val lastPrice: String,
    val lastQty: String,
    val bidQty: String,
    val askQty: String,
    val openPrice: String,
    val highPrice: String,
    val lowPrice: String,
    val volume: String,
    val quoteVolume: String,
    val openTime: Long,
    val closeTime: Long,
    val firstId: Long,
    val lastId: Long,
    val count: Int
)