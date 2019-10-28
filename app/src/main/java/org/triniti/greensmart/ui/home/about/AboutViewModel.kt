package org.triniti.greensmart.ui.home.about

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.db.entities.User
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.ui.home.bins.OnCompletionListener
import org.triniti.greensmart.utilities.Coroutines


class AboutViewModel(private val repository: AuthRepository) : ViewModel() {
    var context: Context? = null
    val user = repository.getUser()
    var listener: OnCompletionListener? = null

    fun getLocation(): String {
        val use = user.value!!

        val geoCoder = Geocoder(context)
        val matches = geoCoder.getFromLocation(use.latitude, use.longitude, 1)
        val bestMatch = if (matches.isEmpty()) null else matches[0]
        return bestMatch.toString()
    }

    fun updateUser(user: User) {
        Coroutines.main {
            val authResponse = repository.userUpdate(user)
            authResponse.user.apply {
                listener?.onCompletion(user)
            }
        }
    }
}