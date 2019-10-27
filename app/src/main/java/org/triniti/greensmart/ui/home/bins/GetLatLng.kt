package org.triniti.greensmart.ui.home.bins

import com.google.android.gms.maps.model.LatLng

interface GetLatLng {
    fun onSuccess(latLng: LatLng)
    fun onFailure(message: String?)
}