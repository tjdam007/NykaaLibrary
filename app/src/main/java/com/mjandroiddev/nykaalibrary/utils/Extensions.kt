package com.mjandroiddev.nykaalibrary.utils

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.mjandroiddev.nykaalibrary.BuildConfig
import java.util.concurrent.TimeUnit
import kotlin.math.min

/*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */

/*
* Debug logs
* */
fun log(message: String) {
    if (BuildConfig.DEBUG) Log.d("Debug Log", message)
}

/*
* Format milliseconds in to min:sec string
* */
fun getTimeString(millis: Long): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    val secCounter = seconds % 60
    return "%02d:%02d".format(minutes, secCounter)
}

/*
* Format milliseconds in to hrs:min:sec string
* */
fun getFullTimeString(millis: Long): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    val hrs = TimeUnit.SECONDS.toHours(seconds)
    val secCounter = seconds % 60
    return "%02d:%02d:%02d".format(hrs, minutes, secCounter)
}


/*
* Prevent from nav controller multiple navigation on same destination
* */
fun NavController.navigateSafe(
    currentDestinationId: Int,
    destinationId: Int,
    bundle: Bundle? = null
) {
    if (this.currentDestination?.id == currentDestinationId) {
        if (bundle == null) {
            navigate(destinationId)
        } else {
            navigate(destinationId, bundle)
        }
    }
}