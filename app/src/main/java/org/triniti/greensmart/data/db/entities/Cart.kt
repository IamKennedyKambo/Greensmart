package org.triniti.greensmart.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Cart(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    var shopId: Int,
    var userId: Int,
    var name: String,//Name
    var price: Int,//Price
    var image: String,//Image
    var points: Int,//Points
    var count: Int,
    var code: String,//Item redeem code
    var description: String,
    var redeemed: Int
) : Parcelable