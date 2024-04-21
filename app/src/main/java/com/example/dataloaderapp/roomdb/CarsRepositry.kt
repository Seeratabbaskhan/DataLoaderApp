package com.example.dataloaderapp.roomdb


import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CarRepository @Inject() constructor(private val carDao: CarsDao) {


    suspend fun insertCarsIfNotExists(cars: List<CarsTable>) {
        withContext(Dispatchers.IO) {
            val currentCars = carDao.getAllCars().value
            if (currentCars.isNullOrEmpty()) {
                carDao.insertAll(cars)
            }
        }
    }


    fun getAllCars(): LiveData<List<CarsTable>> {
        return carDao.getAllCars()
    }

    suspend fun updateFavorite(carId: Int, favorite: Int) {
        withContext(Dispatchers.IO) {
            carDao.updateFavourite(carId, favorite)
        }
    }
}