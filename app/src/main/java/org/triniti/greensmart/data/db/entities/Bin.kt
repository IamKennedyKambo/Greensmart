package org.triniti.greensmart.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bin(
    @PrimaryKey(autoGenerate = false)
    val binId: Int,
    val lat: Double,
    val lang: Double,
    val fill_level: Int
)