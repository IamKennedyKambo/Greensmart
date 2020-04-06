package org.triniti.greensmart.data.repositories

import org.triniti.greensmart.data.db.AppDatabase
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.network.GreenApi
import org.triniti.greensmart.data.network.SafeApiCall
import org.triniti.greensmart.data.network.responses.AuthResponse
import org.triniti.greensmart.data.preferences.PreferenceProvider

class AuthRepository(
    private val api: GreenApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiCall() {

    suspend fun userLogin(user: User): AuthResponse {
        return apiRequest {
            api.userLogin(user)
        }
    }

    suspend fun userSignUp(user: User): AuthResponse {
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

    suspend fun fetchUser(): AuthResponse {
        return apiRequest {
            api.getUser(prefs.getUserId()!!)
        }
    }

    suspend fun logOut() {
        db.getUserDao().clearUserData()
    }


    fun getUser() = db.getUserDao().getUser()
}