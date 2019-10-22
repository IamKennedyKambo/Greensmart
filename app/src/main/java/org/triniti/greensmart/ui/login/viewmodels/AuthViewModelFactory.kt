package org.triniti.greensmart.ui.login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.LoginRepository
import org.triniti.greensmart.ui.login.interfaces.AuthResultCallback

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val repository: LoginRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}