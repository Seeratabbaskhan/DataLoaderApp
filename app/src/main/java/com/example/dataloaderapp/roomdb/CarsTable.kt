package com.example.dataloaderapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cars")
data class CarsTable(
    @PrimaryKey val carID: Int,
    val image: String,
    val titleEn: String,
    val titleAr: String,
    val bids: Int,
    val endTimeInSec: Long,
    val currencyEn: String,
    val currencyAr: String,
    val currentPrice: Int,
    val priority: Int,
    val favourite: Int
)



