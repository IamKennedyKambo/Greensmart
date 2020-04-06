package org.triniti.greensmart.ui.home.stats

import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.repositories.NewsRepository
import org.triniti.greensmart.utilities.lazyDeferred

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel(){

    val news by lazyDeferred {
        newsRepository.getNews()
    }


}