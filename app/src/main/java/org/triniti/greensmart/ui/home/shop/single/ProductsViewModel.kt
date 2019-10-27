package org.triniti.greensmart.ui.home.shop.single

import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.repositories.ProductsRepository
import org.triniti.greensmart.utilities.lazyDeferred

class ProductsViewModel(private val repository: ProductsRepository) : ViewModel() {
    var id: Int? = null

    val items by lazyDeferred {
        repository.getItems(id!!)
    }
}