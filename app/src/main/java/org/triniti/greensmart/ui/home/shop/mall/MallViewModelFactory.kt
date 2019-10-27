package org.triniti.greensmart.ui.home.shop.mall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.MallRepository

@Suppress("UNCHECKED_CAST")
class MallViewModelFactory(private val repository: MallRepository): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MallViewModel(repository) as T
    }
}