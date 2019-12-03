package org.triniti.greensmart.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Product(
    var id: Int,
    var shopId: Int,
    var name: String,//Name
    var price: Int,//Price
    var image: String,//Image
    var points: Int,//Points
    var code: String,//Item redeem code
    var description: String
) : Parcelable