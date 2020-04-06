package org.triniti.greensmart.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.data.preferences.PreferenceProvider

class CartRepository(
    private val api: GreenApi,
    private val prefs: PreferenceProvider
) : SafeApiCall() {

    val cart = MutableLiveData<List<Cart>>()

    suspend fun fetchCart(): LiveData<List<Cart>> {
        try {
            val response = apiRequest {
                api.getCart(prefs.getUserId()!!)
            }
            cart.postValue(response.cart)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cart
    }

    suspend fun insertEntry(cart: Cart) {
        try {
            api.createEntry(cart)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteEntry(id: Int) {
        api.deleteEntry(id)
    }
}