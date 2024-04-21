package com.example.dataloaderapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cars: List<CarsTable>)

    @Query("SELECT * FROM cars")
    fun getAllCars(): LiveData<List<CarsTable>>
}