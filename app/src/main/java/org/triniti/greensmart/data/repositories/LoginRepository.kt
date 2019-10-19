package org.triniti.greensmart.data.repositories

import org.triniti.greensmart.data.db.databases.UserDatabase
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.LoginApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.data.network.responses.AuthResponse

class LoginRepository(private val api: LoginApi, private val db: UserDatabase) : SafeApiCall() {

    suspend fun userLogin(email: String, password: String): AuthResponse {

        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}