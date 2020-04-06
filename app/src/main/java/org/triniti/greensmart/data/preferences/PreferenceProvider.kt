package org.triniti.greensmart.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val BINS_SAVED_AT = "bins_saved_at"
private const val MALLS_SAVED_AT = "malls_saved_at"
private const val NEWS_SAVED_AT = "news_saved_at"
private const val USER_SAVED_AT = "user_saved_at"
private const val USER_ID = "user_id"
private const val TOGGLE_MENU = "toggle_menu"
private const val USED_POINTS = "used_points"

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

    fun saveUsedPoints(points: Int) {
        preference.edit().putInt(
            USED_POINTS,
            points
        ).apply()
    }

    fun getUsedPoints(): Int? {
        return preference.getInt(USED_POINTS, 0)
    }

    fun saveUserId(userId: Int) {
        preference.edit().putInt(USER_ID, userId).apply()
    }

    fun getUserId(): Int? {
        return preference.getInt(USER_ID, 0)
    }

    fun saveValue(show: Boolean) {
        preference.edit().putBoolean(TOGGLE_MENU, show).apply()
    }

    fun getValue(): Boolean? {
        return preference.getBoolean(TOGGLE_MENU, false)
    }

    fun newsLastSavedAt(savedAt: String) {
        preference.edit().putString(NEWS_SAVED_AT, savedAt).apply()
    }

    fun getNewsSavedAt(): String? {
        return preference.getString(NEWS_SAVED_AT, null)
    }
}