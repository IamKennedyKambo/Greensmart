package org.triniti.greensmart.ui.home.about

import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.ui.home.complete.OnCompletionListener
import org.triniti.greensmart.utilities.ApiExceptions
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.NoInternetException
import java.net.ConnectException


class AboutViewModel(private val repository: AuthRepository) : ViewModel() {
    val user = repository.getUser()
    var completionListener: OnCompletionListener? = null

    fun updateUser(user: User) {
        Coroutines.main {
            try {
                val authResponse = repository.userUpdate(user)

                authResponse.user?.let {
                    repository.saveUser(it)
                    completionListener?.onCompletion(user)
                }
                completionListener?.onFailure(authResponse.message!!)

            } catch (a: ApiExceptions) {
                completionListener?.onFailure(a.message!!)
            } catch (e: NoInternetException) {
                completionListener?.onFailure(e.message!!)
            } catch (f: ConnectException) {
                completionListener?.onFailure("Server error, please try again later")
            }
        }
    }

    fun getUser() {
        Coroutines.main {
            try {
                val authResponse = repository.fetchUser()

                authResponse.user?.let {
                    repository.saveUser(it)
                    completionListener?.onCompletion(it)
                }
                completionListener?.onFailure(authResponse.message!!)
            } catch (a: ApiExceptions) {
                completionListener?.onFailure(a.message!!)
            } catch (e: NoInternetException) {
                completionListener?.onFailure(e.message!!)
            } catch (f: ConnectException) {
                completionListener?.onFailure("Server error, please try again later")
            }
        }
    }

    fun logOut() {
        Coroutines.main {
            repository.logOut()
        }
    }
}