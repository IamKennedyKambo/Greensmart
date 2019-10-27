package org.triniti.greensmart.ui.home.bins

import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.repositories.BinsRepository
import org.triniti.greensmart.utilities.lazyDeferred

class BinsViewModel(private val repository: BinsRepository) :
    ViewModel() {

    val bins by lazyDeferred {
        repository.getBins()
    }
}