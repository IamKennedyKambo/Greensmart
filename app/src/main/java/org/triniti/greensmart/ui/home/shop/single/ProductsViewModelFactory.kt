package org.triniti.greensmart.ui.home.shop.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.ProductsRepository

@Suppress("UNCHECKED_CAST")
class ProductsViewModelFactory(private val repository: ProductsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductsViewModel(repository) as T
    }
}