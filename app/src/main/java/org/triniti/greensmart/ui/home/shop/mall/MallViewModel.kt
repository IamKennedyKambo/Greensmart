package org.triniti.greensmart.ui.home.shop.mall

import androidx.lifecycle.ViewModel
import org.triniti.greensmart.data.repositories.MallRepository
import org.triniti.greensmart.utilities.lazyDeferred

class MallViewModel(repository: MallRepository): ViewModel() {

    val shops by lazyDeferred {
        repository.getShops()
    }
}