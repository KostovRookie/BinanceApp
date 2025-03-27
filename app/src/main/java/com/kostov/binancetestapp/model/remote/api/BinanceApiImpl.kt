package com.kostov.binancetestapp.model.remote.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.kostov.binancetestapp.model.BinanceDto


class BinanceApiImpl(
    private val client: HttpClient
) : BinanceApi {

    override suspend fun fetchTrades(): List<BinanceDto> {
        return client.get("https://api2.binance.com/api/v3/ticker/24hr") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}