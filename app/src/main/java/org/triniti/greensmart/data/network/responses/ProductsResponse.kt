package org.triniti.greensmart.data.network.responses

import org.triniti.greensmart.data.db.entities.Product

data class ProductsResponse(val isSuccessful: Boolean?, val items: List<Product>?)