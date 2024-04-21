package com.example.dataloaderapp.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataloaderapp.roomdb.CarRepository
import com.example.dataloaderapp.roomdb.CarsTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(private val repository: CarRepository) : ViewModel() {


    val allCars: LiveData<List<CarsTable>> = repository.getAllCars()


    fun insertCarsIfNotExists(cars: List<CarsTable>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCarsIfNotExists(cars)
        }
    }
}