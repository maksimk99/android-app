package com.brstu.lab.android

import androidx.room.*

@Dao
interface CarSaleDao {
    @Insert
    fun addCarSale(movie: CarSale)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarSales(movies: List<CarSale>)

    @Query("select * from car_sale")
    fun getAllCarSales(): List<CarSale>
}