package com.kostov.binancetestapp.model.remote.api

import com.kostov.binancetestapp.model.BinanceDto

interface BinanceApi {
    suspend fun fetchTrades(): List<BinanceDto>
}
