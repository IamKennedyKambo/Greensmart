package org.triniti.greensmart.data.network.responses

import org.triniti.greensmart.data.db.entities.Items

data class ProductsResponse(val isSuccessful: Boolean?, val items: List<Items>?)