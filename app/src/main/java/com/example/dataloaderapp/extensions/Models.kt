package com.example.dataloaderapp.extensions

import com.google.gson.annotations.SerializedName

data class Car(
    val carID: Int,
    val image: String,
    val titleEn: String,
    val titleAr: String,
    @SerializedName("AuctionInfo")
    val auctionInfo: AuctionInfo
)

data class AuctionInfo(
    val bids: Int,
    val endTimeInSec: Long,
    val currencyEn: String,
    val currencyAr: String,
    val currentPrice: Int,
    val priority: Int
)

class MainModel {
    @SerializedName("Error")
    var error: Error? = null
    @SerializedName("Cars")
    var cars: List<Car>? = null
}