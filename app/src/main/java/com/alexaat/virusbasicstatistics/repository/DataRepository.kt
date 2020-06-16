package com.alexaat.virusbasicstatistics.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexaat.virusbasicstatistics.database.CountriesDatabaseDao
import com.alexaat.virusbasicstatistics.network.CountriesApi
import com.alexaat.virusbasicstatistics.network.CountryStats
import kotlinx.coroutines.*

class DataRepository(private val database: CountriesDatabaseDao){

private var sortMode = SortMode.MAXMIN

private val _countries:MutableLiveData<List<CountryStats>> = MutableLiveData()
val countries: LiveData<List<CountryStats>>
    get() = _countries

private val _loadStatus = MutableLiveData<LoadStatus>(null)
val loadStatus:LiveData<LoadStatus>
    get() = _loadStatus

suspend fun setSortMode(mode:SortMode){
    sortMode = mode
    withContext(Dispatchers.IO) {
        _countries.postValue(when (sortMode) {
            SortMode.AZ -> database.getAllSortByNameASCList()
            SortMode.ZA -> database.getAllSortByNameDESCList()
            SortMode.MINMAX -> database.getAllSortByTotalConfirmedASCList()
            else -> database.getAllTotalConfirmedDESCList()
        })
    }
    refreshCountries()
}

suspend fun refreshCountries(){
        _loadStatus.postValue(LoadStatus.LOADING)
        withContext(Dispatchers.IO) {
            val getGlobalDeferred = CountriesApi.retrofitService.getGlobalAsync()
            try {
                val globalObject = getGlobalDeferred.await()
                val globalStat = CountryStats(
                    CountryCode = "GLOBAL",
                    NewConfirmed = globalObject.Global.NewConfirmed,
                    TotalConfirmed = globalObject.Global.TotalConfirmed,
                    NewDeaths = globalObject.Global.NewDeaths,
                    TotalDeaths = globalObject.Global.TotalDeaths,
                    NewRecovered = globalObject.Global.NewRecovered,
                    TotalRecovered = globalObject.Global.TotalRecovered,
                    Date = globalObject.Date
                )
                database.insert(globalStat)

                for (country in globalObject.Countries) {
                    database.insert(country)
                }
                _loadStatus.postValue(LoadStatus.SUCCESS)
            }catch(e:Exception){
                _loadStatus.postValue(LoadStatus.FAIL)
            }
        }
}

fun loadStatusDisplayComplete(){
    _loadStatus.value = null
}

}

enum class LoadStatus{
    LOADING,
    SUCCESS,
    FAIL
}

enum class SortMode{
    AZ,
    ZA,
    MAXMIN,
    MINMAX

}