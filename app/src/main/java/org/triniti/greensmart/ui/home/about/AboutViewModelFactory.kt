package org.triniti.greensmart.ui.home.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.AuthRepository
import org.triniti.greensmart.data.repositories.BinsRepository

@Suppress("UNCHECKED_CAST")
class AboutViewModelFactory(private val repository: AuthRepository): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AboutViewModel(repository) as T
    }
}