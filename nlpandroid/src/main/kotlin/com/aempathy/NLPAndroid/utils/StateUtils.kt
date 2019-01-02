package com.aempathy.NLPAndroid.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Various functions describing the user/device/app state.
 */
class StateUtils {

    companion object {

        private val TAG = "NLPAndroid: "+StateUtils::class.java.simpleName

        fun isNetworkAvailable(context:Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }
    }
}