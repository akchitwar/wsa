package com.wsa.shows.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService

class NetworkHelper(private val context: Context) {
    /**
     * Detect whether network is available
     */
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService<ConnectivityManager>() ?: return false
        val activeNetwork = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}
