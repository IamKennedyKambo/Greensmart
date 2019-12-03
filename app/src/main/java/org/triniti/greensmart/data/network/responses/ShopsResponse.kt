package org.triniti.greensmart.data.network.responses

import org.triniti.greensmart.data.db.entities.Shop

data class ShopsResponse(
    val isSuccessful: Boolean?,
    val shops: List<Shop>?
)