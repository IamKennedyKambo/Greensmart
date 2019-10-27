package org.triniti.greensmart.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import org.triniti.greensmart.utilities.NoInternetException
import android.net.Network
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService



class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable())
            throw NoInternetException("You are offline")

        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < 23) {
            val ni = cm.activeNetworkInfo

            if (ni != null) {
                return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
            }
        } else {
            val n = cm.activeNetwork

            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)

                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
        }

        return false
    }
}