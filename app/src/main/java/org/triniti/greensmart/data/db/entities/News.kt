package org.triniti.greensmart.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val content: String,
    val seen: Int,
    val image: String
)