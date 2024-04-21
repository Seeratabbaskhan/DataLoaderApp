package com.example.dataloaderapp.di

import android.content.Context
import androidx.room.Room
import com.example.dataloaderapp.R
import com.example.dataloaderapp.roomdb.CarDatabase
import com.example.dataloaderapp.roomdb.CarRepository
import com.example.dataloaderapp.roomdb.CarsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CarDatabase {
        return Room.databaseBuilder(
            appContext,
            CarDatabase::class.java,
            appContext.getString(R.string.carsdatabase_db)
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDBDao(carDatabase: CarDatabase): CarsDao {
        return carDatabase.carDao()
    }

    @Provides
    fun provideRepo(carsDao: CarsDao): CarRepository {
        return CarRepository(carsDao)
    }


}