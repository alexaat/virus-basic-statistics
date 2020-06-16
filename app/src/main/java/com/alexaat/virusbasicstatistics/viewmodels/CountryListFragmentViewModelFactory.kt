package com.alexaat.virusbasicstatistics.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexaat.virusbasicstatistics.database.CountriesDatabaseDao
import com.alexaat.virusbasicstatistics.repository.SortMode

class CountryListFragmentViewModelFactory(private val database: CountriesDatabaseDao, private val sortMode:SortMode): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryListFragmentViewModel::class.java)) {
            return CountryListFragmentViewModel(database = database, sortMode = sortMode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}