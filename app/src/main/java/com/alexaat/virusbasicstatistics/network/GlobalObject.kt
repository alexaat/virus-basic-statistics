package com.alexaat.virusbasicstatistics.network

data class GlobalObject(
    val Global: GlobalStats,
    val Countries:List<CountryStats>,
    val Date:String
)