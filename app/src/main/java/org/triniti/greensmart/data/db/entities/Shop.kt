package org.triniti.greensmart.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shop(
    @PrimaryKey(autoGenerate = false)
    val id: String,//Shop name
    val name: String,//Shop logo
    val logo: String,//Shop id
    val latitude: Double,//Shop location
    val longitude: Double//Shop catalog
)