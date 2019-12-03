package org.triniti.greensmart.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shop(
    @PrimaryKey(autoGenerate = false)
    var id: String,//Shop name
    var name: String,//Shop logo
    var logo: String,//Shop id
    var latitude: Double,//Shop location
    var longitude: Double//Shop catalog
)