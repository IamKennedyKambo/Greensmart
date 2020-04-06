package org.triniti.greensmart.ui.home.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.triniti.greensmart.data.repositories.NewsRepository

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}