package org.triniti.greensmart.ui.auth

import org.triniti.greensmart.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User?)
    fun onFailure(message: String)
}