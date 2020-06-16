package com.alexaat.virusbasicstatistics.database


import androidx.room.*
import com.alexaat.virusbasicstatistics.network.CountryStats

@Dao
interface CountriesDatabaseDao{

@Query("SELECT * FROM country_stats ORDER BY total_confirmed DESC")
fun getAllTotalConfirmedDESCList(): List<CountryStats>

@Query("SELECT * FROM country_stats WHERE country_code = 'GLOBAL' UNION ALL SELECT * FROM (SELECT * FROM country_stats WHERE country_code <> 'GLOBAL' ORDER BY total_confirmed ASC)")
fun getAllSortByTotalConfirmedASCList(): List<CountryStats>

@Query("SELECT * FROM country_stats WHERE country_code = 'GLOBAL' UNION ALL SELECT * FROM (SELECT * FROM country_stats WHERE country_code <> 'GLOBAL' ORDER BY Country ASC)")
fun getAllSortByNameASCList(): List<CountryStats>

@Query("SELECT * FROM country_stats WHERE country_code = 'GLOBAL' UNION ALL SELECT * FROM (SELECT * FROM country_stats WHERE country_code <> 'GLOBAL' ORDER BY Country DESC)")
fun getAllSortByNameDESCList(): List<CountryStats>

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insert(countryStats:CountryStats)

}