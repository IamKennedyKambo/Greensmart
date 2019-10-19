package org.triniti.greensmart.ui.login.interfaces

import org.triniti.greensmart.data.db.entities.User

interface AuthResultCallback {
    fun onStarted(message: String)
    fun onSuccess(user: User?)
    fun onFailure(message: String)
}