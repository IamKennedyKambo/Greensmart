package org.triniti.greensmart.ui.home.shop.single

import org.triniti.greensmart.data.db.entities.Items

interface ProductsListener {
    fun onStarted()
    fun onSuccess(list: List<Items>?)
    fun onFailure(message: String)
}