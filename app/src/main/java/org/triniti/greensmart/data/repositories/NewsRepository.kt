package org.triniti.greensmart.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import org.triniti.greensmart.data.db.AppDatabase
import org.triniti.greensmart.data.db.entities.News
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.utilities.Constants.MINIMUM_INTERVAL_ONE
import org.triniti.greensmart.utilities.Constants.MINIMUM_INTERVAL_TWO
import org.triniti.greensmart.utilities.Coroutines

class NewsRepository(
    private val api: GreenApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiCall() {
    private val news = MutableLiveData<List<News>>()

    init {
        news.observeForever {
            saveNews(it)
        }
    }

    private suspend fun fetchNews() {
        val lastSavedAt = prefs.getNewsSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest { api.getNews() }
                news.postValue(response.news)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getNews(): LiveData<List<News>> {
        return withContext(Dispatchers.IO) {
            fetchNews()
            db.getNewsDao().getNews()
        }
    }

    private fun saveNews(list: List<News>) {
        Coroutines.io {
            prefs.newsLastSavedAt(LocalDateTime.now().toString())
            db.getNewsDao().insertMessages(list)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL_ONE
    }
}