package com.mjandroiddev.nykaalibrary.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mjandroiddev.nykaalibrary.utils.Constants
import com.mjandroiddev.nykaalibrary.workManagers.SessionWorker

/*
* BootReceiver is used for starting/stopping pending session
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            //Show Notification with timer
            val task = OneTimeWorkRequestBuilder<SessionWorker>().build()
            WorkManager.getInstance(context).beginUniqueWork(
                Constants.SESSION_WORKER,
                ExistingWorkPolicy.REPLACE,
                task
            ).enqueue()
        }
    }
}