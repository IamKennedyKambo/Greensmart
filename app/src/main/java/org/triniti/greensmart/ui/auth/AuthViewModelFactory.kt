package org.triniti.greensmart.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.preferences.PreferenceProvider
import org.triniti.greensmart.data.repositories.AuthRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: AuthRepository,
    private val prefs: PreferenceProvider
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository, prefs) as T
    }
}