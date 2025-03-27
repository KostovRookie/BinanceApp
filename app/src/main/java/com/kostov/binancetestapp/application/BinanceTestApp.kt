package com.kostov.binancetestapp.application

import android.app.Application
import com.kostov.binancetestapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BinanceTestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BinanceTestApp)
            modules(appModule)
        }
    }
}