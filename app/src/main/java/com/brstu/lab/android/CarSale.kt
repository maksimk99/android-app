package com.brstu.lab.android

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_sale")
data class CarSale(
    @PrimaryKey
    val carId: String,
    @ColumnInfo(name = "title")
    val name: String,
    val year: String,
    val mileage: String,
    val engineCapacity: String,
    val price: String,
    val imageUrl: String
)
