package org.triniti.greensmart.ui.auth.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.ui.auth.interfaces.AuthListener
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.NoInternetException

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var authListener: AuthListener? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }

    }


    fun onSignUpButtonClicked(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter a password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }

    }
}