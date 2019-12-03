package org.triniti.greensmart.data.network.responses

import org.triniti.greensmart.data.db.entities.Cart

data class CartResponse(
    val isSuccessful: Boolean?,
    val cart: List<Cart>?
)