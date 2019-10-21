package org.triniti.greensmart.data.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int? = null,
    val shopId: Int? = null,
    val name: String? = null,//Name
    val price: String? = null,//Price
    val points: Int? = null,//Points
    val imageUrl: String? = null,//Image
    val code: String? = null,//Item redeem code
    val barCode: String? = null
) : Parcelable