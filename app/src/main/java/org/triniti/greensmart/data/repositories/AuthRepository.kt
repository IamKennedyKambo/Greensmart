package org.triniti.greensmart.data.repositories

import org.triniti.greensmart.data.db.AppDatabase
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.data.network.responses.AuthResponse

class AuthRepository(private val api: GreenApi, private val db: AppDatabase) : SafeApiCall() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        val user = User(email = email, password = password)
        return apiRequest {
            api.userLogin(user)
        }
    }

    suspend fun userSignUp(name: String, email: String, password: String): AuthResponse {
        val user = User(name = name, email = email, password = password)
        return apiRequest {
            api.userSignup(
                user
            )
        }
    }

    suspend fun userUpdate(user: User): AuthResponse {
        return apiRequest {
            api.userUpdate(user.id!!, user)
        }
    }

    suspend fun saveUser(user: User) {
        db.getUserDao().upsert(user)
    }


    fun getUser() = db.getUserDao().getUser()
}