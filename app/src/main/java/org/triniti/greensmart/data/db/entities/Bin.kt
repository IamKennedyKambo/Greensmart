package org.triniti.greensmart.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bin(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var latitude: Double,
    var longitude: Double,
    var fill_level: Int
)