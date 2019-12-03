package org.triniti.greensmart.ui.home.about

import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.ui.home.complete.OnCompletionListener
import org.triniti.greensmart.utilities.Coroutines


class AboutViewModel(private val repository: AuthRepository) : ViewModel() {
    val user = repository.getUser()
    var listener: OnCompletionListener? = null

    fun updateUser(user: User) {
        Coroutines.main {
            val authResponse = repository.userUpdate(user)

            authResponse.user?.let {
                repository.saveUser(it)
                listener?.onCompletion(user)
            }
        }
    }
}