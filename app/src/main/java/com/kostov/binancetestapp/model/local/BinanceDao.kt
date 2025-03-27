package com.kostov.binancetestapp.model.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase

@Dao
interface BinanceDao {
    @Query("SELECT * FROM binance")
    suspend fun getAll(): List<BinanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(binance: List<BinanceEntity>)
}

@Database(entities = [BinanceEntity::class], version = 1, exportSchema = false)
abstract class BinanceDatabase : RoomDatabase() {
    abstract fun binanceDao(): BinanceDao
}