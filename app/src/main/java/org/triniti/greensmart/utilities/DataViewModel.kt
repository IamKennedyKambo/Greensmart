package org.triniti.greensmart.utilities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.db.entities.Product

class DataViewModel : ViewModel() {

    val product: MutableLiveData<Product> = MutableLiveData()
    val cart: MutableLiveData<Cart> = MutableLiveData()
    val success: MutableLiveData<Boolean> = MutableLiveData()
    val complete: MutableLiveData<Boolean> = MutableLiveData()

    fun setProduct(product: Product) {
        this.product.postValue(product)
    }

    fun setCart(cart: Cart) {
        this.cart.postValue(cart)
    }

    fun setSuccess(value: Boolean) {
        success.postValue(value)
    }

    fun updateComplete(yes: Boolean){
        complete.postValue(yes)
    }
}