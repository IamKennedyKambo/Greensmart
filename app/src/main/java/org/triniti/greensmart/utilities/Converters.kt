package org.triniti.greensmart.utilities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.triniti.greensmart.data.db.entities.Bin

class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun toList(value: String): List<String> {
            val listType = object : TypeToken<List<String>>() {

            }.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromList(list: List<String>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun makeString(list: List<Bin>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun fromString(value: String): List<Bin> {
            val listType = object : TypeToken<List<Bin>>() {

            }.type
            return Gson().fromJson(value, listType)
        }
    }
}