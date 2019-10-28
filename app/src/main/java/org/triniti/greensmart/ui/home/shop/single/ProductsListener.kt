package org.triniti.greensmart.ui.home.shop.single

import org.triniti.greensmart.data.db.entities.Product

interface ProductsListener {
    fun onStarted()
    fun onSuccess(list: List<Product>?)
    fun onFailure(message: String)
}