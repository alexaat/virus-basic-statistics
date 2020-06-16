package com.alexaat.virusbasicstatistics.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alexaat.virusbasicstatistics.database.CountriesDatabase
import com.alexaat.virusbasicstatistics.repository.DataRepository
import com.bumptech.glide.load.HttpException


class RefreshDataWorker(val context: Context, params:WorkerParameters):CoroutineWorker(context,params){
    companion object{
        const val WORK_NAME = "com.alexaat.virusbasicstatistics.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = CountriesDatabase.getInstance(context).countriesDatabaseDao
        val repository = DataRepository(database)

        return try {
            repository.refreshCountries()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }


}