package com.kostov.binancetestapp.model

import com.kostov.binancetestapp.model.local.BinanceDao
import com.kostov.binancetestapp.model.local.BinanceEntity
import com.kostov.binancetestapp.model.remote.api.BinanceApi

class BinanceRepository(
    private val api: BinanceApi,
    private val dao: BinanceDao
) {
    suspend fun fetchAndCacheCryptoTrades(): List<BinanceEntity> {
        return try {
            val remoteData = api.fetchTrades()
            val entities = remoteData.map { it.toEntity() }
            dao.insertAll(entities)
            entities
        } catch (_: Exception) {
            dao.getAll()
        }
    }

    fun BinanceDto.toEntity() = BinanceEntity(
        symbol = symbol,
        priceChange = priceChange,
        priceChangePercent = priceChangePercent,
        bidPrice = bidPrice,
        askPrice = askPrice
    )
}