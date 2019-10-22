package org.triniti.greensmart.data.db.entities

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID: Int = 0

@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var date_joined: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var usable_points: Int? = null,
    var available_points: Int? = null,
    var level: Int? = null,
    var cardId: String? = null
) : BaseObservable() {

    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID

    val isDataValid: Boolean
        get() = (!TextUtils.isEmpty(getMail()))
                && Patterns.EMAIL_ADDRESS.matcher(getMail().toString()).matches()
                && getPass().toString().length >= 6

    private fun getPass(): String? {
        return password
    }

    private fun getMail(): String? {
        return email
    }

    fun setMail(email: String) {
        this.email = email
    }

    fun setPass(password: String) {
        this.password = password
    }

//    var ui: Int = 0,
//    var un: String = "",//Username
//    var em: String = "",//Email
//    var pa: String = "",//Password
//    var up: Int = 0,//Usable points
//    var ap: Int = 0,//Available points
//    //    var lo: LatLng = LatLng(0.0, 0.0),//Location
//    var lv: Int = 0//Level
////    var li: List<Product> = mutableListOf()//Products
}