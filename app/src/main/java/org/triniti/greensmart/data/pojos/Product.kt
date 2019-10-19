package org.triniti.greensmart.data.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int? = null,
    val si: Int? = null,
    val nm: String? = null,//Name
    val pr: String? = null,//Price
    val po: Int? = null,//Points
    val im: String? = null,//Image
    val co: String? = null//Item redeem code
) : Parcelable