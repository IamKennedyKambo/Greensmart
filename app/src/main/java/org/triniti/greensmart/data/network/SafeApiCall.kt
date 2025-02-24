package org.triniti.greensmart.data.network

import org.json.JSONException
import org.json.JSONObject
import org.triniti.greensmart.utilities.ApiExceptions
import retrofit2.Response

abstract class SafeApiCall {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                }
            }

            throw ApiExceptions(message.toString())
        }
    }
}