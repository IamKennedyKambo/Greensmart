package org.triniti.greensmart.data.network.responses

import org.triniti.greensmart.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)