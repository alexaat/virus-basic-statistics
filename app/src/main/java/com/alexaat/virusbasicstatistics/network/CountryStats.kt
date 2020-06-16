package com.alexaat.virusbasicstatistics.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_stats")
data class CountryStats(
    @PrimaryKey
    val Country:String="",
    @ColumnInfo(name = "country_code")
    val CountryCode:String="",
    @ColumnInfo(name = "slug")
    val Slug:String="",
    @ColumnInfo(name = "new_confirmed")
    val NewConfirmed:Long,
    @ColumnInfo(name = "total_confirmed")
    val TotalConfirmed:Long,
    @ColumnInfo(name = "new_deaths")
    val NewDeaths:Long,
    @ColumnInfo(name = "total_deaths")
    val TotalDeaths:Long,
    @ColumnInfo(name = "new_recovered")
    val NewRecovered:Long,
    @ColumnInfo(name = "total_recovered")
    val TotalRecovered:Long,
    @ColumnInfo(name = "date")
    val Date:String
)


