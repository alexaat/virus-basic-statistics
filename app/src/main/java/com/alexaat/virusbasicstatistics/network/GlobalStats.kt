package com.alexaat.virusbasicstatistics.network

data class GlobalStats(
    val NewConfirmed:Long,
    val TotalConfirmed:Long,
    val NewDeaths:Long,
    val TotalDeaths:Long,
    val NewRecovered:Long,
    val TotalRecovered:Long
)