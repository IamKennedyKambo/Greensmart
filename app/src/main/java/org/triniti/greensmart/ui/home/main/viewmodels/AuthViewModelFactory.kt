package org.triniti.greensmart.ui.home.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.data.repositories.BinsRepository
import org.triniti.greensmart.ui.auth.viewmodels.AuthViewModel

@Suppress("UNCHECKED_CAST")
class BinsViewModelFactory(private val repository: BinsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BinsViewModel(repository) as T
    }
}