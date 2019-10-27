package org.triniti.greensmart.data.db.entities

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID: Int = 0

@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String,
    var password: String,
    var about: String? = "",
    var date_joined: String? = null,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var usable_points: Int? = 0,
    var available_points: Int? = 0,
    var level: Int? = 1,
    var cardId: String? = ""
) : BaseObservable() {

    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}