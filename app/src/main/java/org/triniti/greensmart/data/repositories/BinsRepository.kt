package org.triniti.greensmart.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.triniti.greensmart.data.db.AppDatabase
import org.triniti.greensmart.data.db.entities.Bin
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.utilities.Coroutines

class BinsRepository(private val api: GreenApi, private val db: AppDatabase) : SafeApiCall() {
    private val bins = MutableLiveData<List<Bin>>()

    init {
        bins.observeForever {
            saveBins(it)
        }
    }

    private suspend fun fetchQuotes() {
        if (isFetchNeeded()) {
            val response = apiRequest {
                api.getBins()
            }
            bins.postValue(response.bins)
        }
    }

    suspend fun getBins(): LiveData<List<Bin>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getBinsDao().getBins()
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    private fun saveBins(list: List<Bin>) {
        Coroutines.io {
            db.getBinsDao().saveBins(list)
        }
    }
}