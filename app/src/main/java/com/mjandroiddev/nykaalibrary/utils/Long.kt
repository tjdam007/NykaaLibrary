package com.mjandroiddev.nykaalibrary.utils

import java.text.SimpleDateFormat
import java.util.*
/*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */


/*
* Convert long to Time String
* */
fun Long.toTime(): String {
    return if (this > 0) {
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(this))
    } else {
        "--"
    }
}

/*
* Convert long to Date
* */
fun Long.toDate(): String {
    return if (this > 0) {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(this))
    } else {
        "--"
    }
}