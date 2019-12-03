package org.triniti.greensmart.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall

class CartRepository(
    private val api: GreenApi
) : SafeApiCall() {

    private val cart = MutableLiveData<List<Cart>>()

    suspend fun fetchCart(userId: Int): LiveData<List<Cart>> {
        try {
            val response = apiRequest {
                api.getCart(userId)
            }
            cart.postValue(response.cart)
            Log.i("From network", cart.value?.size.toString())
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
}