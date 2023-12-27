package com.wsa.shows.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class NetworkHelper(private val context: Context) {
    /**
     * Detect whether network is available
     */

    private val _networkStatus = MutableStateFlow(false)
    val networkStatus = _networkStatus.asStateFlow()
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService<ConnectivityManager>() ?: return false
        val activeNetwork = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}
