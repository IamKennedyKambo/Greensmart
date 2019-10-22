package org.triniti.greensmart.ui.login.viewmodels

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.repositories.LoginRepository
import org.triniti.greensmart.ui.login.interfaces.AuthResultCallback
import org.triniti.greensmart.utilities.ApiExceptions
import org.triniti.greensmart.utilities.Coroutines
import org.triniti.greensmart.utilities.NoInternetException

class AuthViewModel(
    private val repository: LoginRepository
) : ViewModel() {
    private val user: User = User()
    var listener: AuthResultCallback? = null

    val emailTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                user.setMail(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }

    val passwordTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                user.setPass(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }

    fun getCurrentUser() = repository.getUser()

    fun onLoginButtonClicked(view: View) {
        listener?.onStarted("Login started")
        if (!user.isDataValid) {
            listener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(user.email!!, user.password!!)
                authResponse.user?.let {
                    listener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }

                listener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                listener?.onFailure(e.message!!)
            } catch (n: NoInternetException) {
                listener?.onFailure(n.message!!)
            }
        }
    }

    fun onSignUpButtonClicked(view: View) {
        listener?.onStarted("Login started")
        if (!user.isDataValid) {
            listener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            try {
                val authResponse =
                    repository.userSignUp(user.name!!, user.email!!, user.password!!)
                authResponse.user?.let {
                    listener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }

                listener?.onFailure(authResponse.message!!)
            } catch (e: ApiExceptions) {
                listener?.onFailure(e.message!!)
            } catch (n: NoInternetException) {
                listener?.onFailure(n.message!!)
            }
        }
    }
}