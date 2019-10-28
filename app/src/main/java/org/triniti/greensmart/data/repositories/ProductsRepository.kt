package org.triniti.greensmart.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.triniti.greensmart.data.db.entities.Product
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall

class ProductsRepository(
    private val api: GreenApi
) : SafeApiCall() {

    private val items = MutableLiveData<List<Product>>()

    private suspend fun fetchItems(id: Int) {
        try {
            val response = apiRequest {
                api.fetchItems(id)
            }
            items.postValue(response.items)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getItems(id: Int): LiveData<List<Product>> {
        return withContext(Dispatchers.IO) {
            fetchItems(id)
            items
        }
    }
}