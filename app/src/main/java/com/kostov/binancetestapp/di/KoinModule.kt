package com.kostov.binancetestapp.di

import androidx.room.Room
import com.kostov.binancetestapp.model.BinanceRepository
import com.kostov.binancetestapp.model.local.BinanceDatabase
import com.kostov.binancetestapp.model.remote.api.BinanceApi
import com.kostov.binancetestapp.model.remote.api.BinanceApiImpl
import com.kostov.binancetestapp.presentation.BinanceViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    single<BinanceApi> { BinanceApiImpl(get()) }

    single {
        Room.databaseBuilder(get(), BinanceDatabase::class.java, "tickers.db").build()
    }

    single { get<BinanceDatabase>().binanceDao() }

    single { BinanceRepository(get(), get()) }

    viewModel { BinanceViewModel(get()) }
}