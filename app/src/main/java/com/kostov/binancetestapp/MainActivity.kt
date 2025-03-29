package com.kostov.binancetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kostov.binancetestapp.presentation.BinanceViewModel
import com.kostov.binancetestapp.presentation.state.BinanceListRoute
import com.kostov.binancetestapp.presentation.ui.screens.WelcomeDialog
import com.kostov.binancetestapp.ui.theme.BinanceTestAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: BinanceViewModel = koinViewModel()
            var showDialog by remember { mutableStateOf(true) }

            BinanceTestAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    LaunchedEffect(Unit) {
                        viewModel.loadTrades()
                    }

                    if (showDialog) {
                        WelcomeDialog(
                            isDataLoaded = viewModel.isDataLoaded.collectAsState().value,
                            onFinish = { showDialog = false }
                        )
                    } else {
                        BinanceListRoute ()
                    }
                }
            }
        }
    }
}