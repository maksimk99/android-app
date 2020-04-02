package com.brstu.lab.android

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CarSale::class], version = 1)
abstract class CarSaleDatabase : RoomDatabase() {
    abstract fun getMovieDao(): CarSaleDao
}