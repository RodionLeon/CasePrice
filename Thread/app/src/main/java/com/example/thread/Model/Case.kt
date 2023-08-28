package com.example.thread.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cases")
data class Case(
    var lowest_price: String,
    var median_price: String,
    var volume: String,
    @PrimaryKey
    val name: String
)