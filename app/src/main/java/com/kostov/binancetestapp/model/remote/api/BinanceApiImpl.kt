package com.kostov.binancetestapp.model.remote.api

import com.kostov.binancetestapp.model.BinanceDto
import com.kostov.binancetestapp.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType


class BinanceApiImpl(private val client: HttpClient) : BinanceApi {

    override suspend fun fetchTrades(): List<BinanceDto> {
        return client.get(Constants.BASE_URL) {
            contentType(ContentType.Application.Json)
        }.body()
    }
}