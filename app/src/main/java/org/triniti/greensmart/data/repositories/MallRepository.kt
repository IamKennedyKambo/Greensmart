package org.triniti.greensmart.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import org.triniti.greensmart.data.db.AppDatabase
import org.triniti.greensmart.data.db.entities.Shop
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.utilities.Coroutines

private const val MINIMUM_INTERVAL = 12

class MallRepository(
    private val api: GreenApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
): SafeApiCall() {

    private val shops = MutableLiveData<List<Shop>>()

    init {
        shops.observeForever {
            saveShops(it)
        }
    }

    private suspend fun fetchShops() {
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest{ api.getShops() }
                shops.postValue(response.shops)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getShops(): LiveData<List<Shop>> {
        return withContext(Dispatchers.IO) {
            fetchShops()
            db.getShopDao().getShops()
        }
    }

    private fun saveShops(list: List<Shop>) {
        Coroutines.io {
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getShopDao().saveShops(list)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.SECONDS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

}