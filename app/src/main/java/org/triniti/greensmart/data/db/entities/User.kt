package org.triniti.greensmart.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID: Int = 0

@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var about: String? = "",
    var date_joined: String? = null,
    var usable_points: Int? = 10,
    var used_points: Int? = 0,
    var level: Int? = 1,
    var cardId: String? = ""
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}