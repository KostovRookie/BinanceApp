package com.kostov.binancetestapp.model

import com.kostov.binancetestapp.model.local.BinanceDao
import com.kostov.binancetestapp.model.local.BinanceEntity
import com.kostov.binancetestapp.model.remote.api.BinanceApi

class BinanceRepository(
    private val api: BinanceApi,
    private val dao: BinanceDao
) {

    suspend fun fetchAndCacheCryptoTrades(): List<BinanceEntity> {
        val remoteData = api.fetchTrades()
        val entities = remoteData.map { it.toEntity() }
        dao.insertAll(entities)
        return entities
    }

    suspend fun getCachedTrades(): List<BinanceEntity> {
        return dao.getAll()
    }

    fun BinanceDto.toEntity() = BinanceEntity(
        symbol = symbol,
        priceChange = priceChange,
        priceChangePercent = priceChangePercent,
        bidPrice = bidPrice,
        askPrice = askPrice
    )
}