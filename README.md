# Binance Crypto Prices App 🟡

A simple, modern, and responsive Android app displaying live and cached crypto prices from the Binance public API, using:
- Kotlin + Jetpack Compose
- MVVM + Repository pattern
- Koin for Dependency Injection
- Room Database for offline support
- Custom splash screen & UI animations
- Pull-to-refresh & search functionality
- Offline fallback when internet is unavailable

---

## ✨ Features
✅ Live crypto prices from Binance  
✅ Offline mode with cached data  
✅ Smooth splash screen with animation  
✅ Search coins by name or symbol (BTC, ETH, etc.)  
✅ Pull-to-refresh mechanism  
✅ Modern Binance-like UI with:
  - Colored % change indicators
  - Crypto pair cards
  - Responsive design
✅ Clean architecture (MVVM + Repository)  
✅ Dependency Injection via Koin  
✅ Local caching using Room  
✅ Network status detection

---

## ⚙️ Tech Stack
- **Kotlin**
- **Jetpack Compose** (UI)
- **MVVM** (Architecture)
- **Room** (Local Database)
- **Koin** (Dependency Injection)
- **Retrofit** (Networking)
- **Binance Public API**
- **StateFlow** & **Coroutines**

---

## ✅ How it works
1. App starts with an animated welcome splash screen
2. Trades are loaded:
   - If internet is available: loads from Binance and caches locally
   - If offline: loads from local database automatically
3. Search bar lets you filter by symbol or coin
4. Pull-to-refresh is available anytime
5. Online / Offline status is displayed under the search bar
