package org.triniti.greensmart.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager

private const val BINS_SAVED_AT = "bins_saved_at"
private const val MALLS_SAVED_AT = "malls_saved_at"
private const val USER_ID = "user_id"

class PreferenceProvider(
    context: Context
) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun binsLastSavedAt(savedAt: String) {
        preference.edit().putString(
            BINS_SAVED_AT,
            savedAt
        ).apply()
    }

    fun getBinsSavedAt(): String? {
        return preference.getString(BINS_SAVED_AT, null)
    }

    fun mallsLastSavedAt(savedAt: String) {
        preference.edit().putString(
            MALLS_SAVED_AT,
            savedAt
        ).apply()
    }

    fun getMallsSavedAt(): String? {
        return preference.getString(MALLS_SAVED_AT, null)
    }

    fun saveUserId(userId: Int) {
        Log.i("UserId", userId.toString())
        preference.edit().putInt(USER_ID, userId).apply()
    }

    fun getUserId(): Int? {
        return preference.getInt(USER_ID, 0)
    }

}