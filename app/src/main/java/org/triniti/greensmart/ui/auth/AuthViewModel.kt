package org.triniti.greensmart.ui.auth

import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.utilities.ApiExceptions
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.NoInternetException
import java.net.ConnectException
import java.net.SocketTimeoutException

class AuthViewModel(
    private val repository: AuthRepository,
    private val prefs: PreferenceProvider
) : ViewModel() {
    var authListener: AuthListener? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null

    fun getLoggedInUser() = repository.getUser()

    fun saveUser(user: User) {
        Coroutines.main {
            repository.saveUser(user)
        }
    }

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Email and password required")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) {
            authListener?.onFailure("Invalid email")
            return
        }

        if (password!!.length < 6) {
            authListener?.onFailure("Password is too short.")
            return
        }

        val user = User(email = email, password = password)

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(user)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    prefs.saveUserId(it.id!!)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (c: ApiExceptions) {
                authListener?.onFailure(c.message!!)
            } catch (f: NoInternetException) {
                authListener?.onFailure(f.message!!)
            } catch (g: SocketTimeoutException) {
                authListener?.onFailure("Account not found.")
            } catch (f: ConnectException) {
                authListener?.onFailure("Server error, please try again later")
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

        if (!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()) {
            authListener?.onFailure("Invalid email")
            return
        }

        if (password!!.length < 6) {
            authListener?.onFailure("Password is too short.")
            return
        }

        val user = User(name = name, email = email, password = password)

        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(user)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    prefs.saveUserId(it.id!!)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (a: ApiExceptions) {
                authListener?.onFailure(a.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            } catch (f: ConnectException) {
                authListener?.onFailure("Server error, please try again later")
            }
        }
    }
}