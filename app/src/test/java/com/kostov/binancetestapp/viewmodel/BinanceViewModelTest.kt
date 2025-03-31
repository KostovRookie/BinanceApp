package com.kostov.binancetestapp.viewmodel

import android.content.Context
import com.kostov.binancetestapp.model.BinanceRepository
import com.kostov.binancetestapp.model.local.BinanceEntity
import com.kostov.binancetestapp.presentation.BinanceViewModel
import com.kostov.binancetestapp.utils.NetworkUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BinanceViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: BinanceRepository
    private lateinit var context: Context

    private lateinit var viewModel: BinanceViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        context = mockk()

        viewModel = BinanceViewModel(repository, context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTrades loads cached data when offline`() = runTest {
        val fakeTrades = listOf(
            BinanceEntity("BTCUSDT", "100", "1.5%", "50000", "50010")
        )

        mockkObject(NetworkUtils)
        every { NetworkUtils.isNetworkAvailable(context) } returns false
        coEvery { repository.getCachedTrades() } returns fakeTrades

        viewModel.loadTrades()

        val uiState = viewModel.uiState.value
        assertEquals(false, uiState.isOnline)
        assertEquals(false, uiState.isRefreshing)
        assertEquals(fakeTrades, uiState.trades)
    }

    @Test
    fun `loadTrades loads remote data when online`() = runTest {
        val fakeTrades = listOf(
            BinanceEntity("BTCUSDT", "100", "1.5%", "50000", "50010")
        )

        mockkObject(NetworkUtils)
        every { NetworkUtils.isNetworkAvailable(context) } returns true
        coEvery { repository.fetchAndCacheCryptoTrades() } returns fakeTrades

        viewModel.loadTrades()

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isOnline)
        assertEquals(false, uiState.isRefreshing)
        assertEquals(fakeTrades, uiState.trades)
    }

    @Test
    fun `onSearchQueryChange updates search query`() = runTest {
        viewModel.onSearchQueryChange("BTC")

        val uiState = viewModel.uiState.value
        assertEquals("BTC", uiState.searchQuery)
    }

    @Test
    fun `clearSearch clears search query`() = runTest {
        viewModel.onSearchQueryChange("BTC")

        viewModel.clearSearch()

        val uiState = viewModel.uiState.value
        assertEquals("", uiState.searchQuery)
    }
}