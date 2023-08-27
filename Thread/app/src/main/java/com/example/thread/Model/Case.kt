package com.example.thread.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cases")
data class Case(
    val lowest_price: String,
    val median_price: String,
    val volume: String,
    @PrimaryKey val name: String
)