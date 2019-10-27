package org.triniti.greensmart.ui.home.bins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.BinsRepository

@Suppress("UNCHECKED_CAST")
class BinsViewModelFactory(private val repository: BinsRepository): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BinsViewModel(repository) as T
    }
}