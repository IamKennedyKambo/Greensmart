package org.triniti.greensmart.data.db.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val shopId: Int,
    val name: String,//Name
    val price: Int,//Price
    val image: String,//Image
    val points: Int,//Points
    val code: String,//Item redeem code
    val description: String
) : Parcelable