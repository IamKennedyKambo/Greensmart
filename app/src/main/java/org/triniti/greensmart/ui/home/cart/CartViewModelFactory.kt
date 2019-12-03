package org.triniti.greensmart.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.CartRepository

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory(private val repository: CartRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CartViewModel(repository) as T
    }
}