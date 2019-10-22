package org.triniti.greensmart.data.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import org.triniti.greensmart.utilities.NoInternetException

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isNetworkAvailable())
            throw NoInternetException("You are offline")

        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(): Boolean {
        val connectionManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectionManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}