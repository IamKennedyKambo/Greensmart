package org.triniti.greensmart.data.pojos

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import org.triniti.greensmart.data.pojos.Product

@Parcelize
data class Shop(
    val sn: String = "",//Shop name
    val sl: String = "",//Shop logo
    val si: String = "",//Shop id
    val lo: LatLng = LatLng(0.0, 0.0),//Shop location
    val it: List<Product> = mutableListOf()//Shop catalog
): Parcelable