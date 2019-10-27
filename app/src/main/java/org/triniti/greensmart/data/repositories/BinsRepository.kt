package org.triniti.greensmart.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import org.triniti.greensmart.data.db.AppDatabase
import org.triniti.greensmart.data.db.entities.Bin
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.utilities.Coroutines

private const val MINIMUM_INTERVAL = 10

class BinsRepository(
    private val api: GreenApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiCall() {
    private val bins = MutableLiveData<List<Bin>>()

    init {
        bins.observeForever {
            saveBins(it)
        }
    }

    suspend fun fetchBins() {
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest { api.getBins() }
                bins.postValue(response.bins)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getBins(): LiveData<List<Bin>> {
        return withContext(Dispatchers.IO) {
            fetchBins()
            db.getBinsDao().getBins()
        }
    }

    private fun saveBins(list: List<Bin>) {
        Coroutines.io {
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getBinsDao().saveBins(list)
        }
    }

    suspend fun clearBins() {
        db.getBinsDao().clearBins()
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.MINUTES.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

}