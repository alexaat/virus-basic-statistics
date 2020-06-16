package com.alexaat.virusbasicstatistics.viewmodels

import androidx.lifecycle.ViewModel
import com.alexaat.virusbasicstatistics.database.CountriesDatabaseDao
import com.alexaat.virusbasicstatistics.repository.DataRepository
import com.alexaat.virusbasicstatistics.repository.SortMode
import kotlinx.coroutines.*

class CountryListFragmentViewModel(database: CountriesDatabaseDao, val sortMode: SortMode): ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val repository = DataRepository(database)
    val countries = repository.countries

    init{
        coroutineScope.launch {
            repository.setSortMode(sortMode)
        }
    }

    fun setSortMode(mode:SortMode){
        coroutineScope.launch {
            repository.setSortMode(mode)
        }
    }

    fun loadStatusDisplayComplete(){
        repository.loadStatusDisplayComplete()
    }

}



