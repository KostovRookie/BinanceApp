package com.kostov.binancetestapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "binance")
data class BinanceEntity(
    @PrimaryKey val symbol: String,
    val priceChange: String,
    val priceChangePercent: String,
    val bidPrice: String,
    val askPrice: String
)