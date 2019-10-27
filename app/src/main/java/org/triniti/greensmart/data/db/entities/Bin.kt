package org.triniti.greensmart.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bin(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val fill_level: Int
)