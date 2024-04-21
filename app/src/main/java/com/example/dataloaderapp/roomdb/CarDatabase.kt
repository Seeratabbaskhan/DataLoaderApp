package com.example.dataloaderapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CarsTable::class], version = 1 , exportSchema = false)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDao(): CarsDao

}