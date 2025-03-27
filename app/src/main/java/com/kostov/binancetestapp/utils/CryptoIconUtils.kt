package com.kostov.binancetestapp.utils

fun getCryptoIconUrl(symbol: String): String {
    return "https://cdn.jsdelivr.net/gh/spothq/cryptocurrency-icons@master/128/color/${symbol.lowercase()}.png"
}

fun splitBinanceSymbol(symbol: String): Pair<String, String> {
    val knownQuoteAssets = listOf("USDT", "BTC", "ETH", "BNB", "BUSD", "USDC", "TRY")

    val quoteAsset = knownQuoteAssets.firstOrNull { symbol.endsWith(it) } ?: return symbol to ""
    val baseAsset = symbol.removeSuffix(quoteAsset)

    return baseAsset to quoteAsset
}