package com.example.thread.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cases")
data class Case(
    var lowest_price: String,
    var median_price: String,
    var volume: String,
    var buyPriceDifference: Double = 0.0,
    var sellPriceDifference: Double = 0.0,
    var sellPriceComparison: Boolean = false,
    var buyPriceComparison: Boolean = false,
    @PrimaryKey
    val name: String
)