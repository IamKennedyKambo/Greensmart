package org.triniti.greensmart.ui.home.cart

import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.db.entities.Cart
import org.triniti.greensmart.data.repositories.CartRepository
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.lazyDeferred

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val cart by lazyDeferred {
        repository.fetchCart()
    }

    fun insertEntry(cart: Cart) {
        Coroutines.main {
            repository.insertEntry(cart)
            repository.fetchCart()
        }
    }

    fun deleteEntry(id: Int) {
        Coroutines.main {
            repository.deleteEntry(id)
            repository.fetchCart()
        }
    }
}