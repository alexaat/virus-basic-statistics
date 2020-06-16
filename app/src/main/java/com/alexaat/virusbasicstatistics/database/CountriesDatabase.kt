package com.alexaat.virusbasicstatistics.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexaat.virusbasicstatistics.network.CountryStats

@Database(entities = [CountryStats::class], version = 1, exportSchema = false)
abstract class CountriesDatabase : RoomDatabase() {

    abstract val countriesDatabaseDao: CountriesDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: CountriesDatabase? = null

        fun getInstance(context: Context): CountriesDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CountriesDatabase::class.java,
                        "work_day_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}