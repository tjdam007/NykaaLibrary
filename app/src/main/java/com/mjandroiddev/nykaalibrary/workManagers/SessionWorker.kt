package com.mjandroiddev.nykaalibrary.workManagers

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.mjandroiddev.nykaalibrary.R
import com.mjandroiddev.nykaalibrary.repositories.SessionRepo
import com.mjandroiddev.nykaalibrary.ui.MainActivity
import com.mjandroiddev.nykaalibrary.utils.getTimeString
import com.mjandroiddev.nykaalibrary.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import java.util.*
import java.util.concurrent.TimeUnit
/*
* Session worker : Long running task for maintaining
* session timer with notification
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
class SessionWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val repo = SessionRepo(context.applicationContext as Application)
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        //Get the Active session
        val activeSession = repo.getActiveSession().firstOrNull()
        activeSession?.apply {
            val sec = TimeUnit.MILLISECONDS.toSeconds(activeSession.pendingSessionTime ?: 0)
            for (s in sec downTo 0) {
                val millis = TimeUnit.SECONDS.toMillis(s)
                setForeground(createForegroundInfo(getTimeString(millis)))
                activeSession.pendingSessionTime = millis
                activeSession.date = Date().time
                repo.insertOrUpdate(activeSession)
                delay(1000)
            }
            activeSession.isActive = false
            activeSession.outTime = Date().time
            repo.insertOrUpdate(activeSession)
            log(activeSession.toString())
        }
        return Result.success()
    }


    /*
    * Update Notification info as per the timer update
    * */
    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val id = applicationContext.getString(R.string.notification_channel_id)
        val title = applicationContext.getString(R.string.notification_title)

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        // Open activity from Notification
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val contentIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // setup Notification
        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentIntent(contentIntent)
            .setContentTitle(title)
            .setContentText(context.getString(R.string.time_remaining, progress))
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setOngoing(true)
            .build()

        return ForegroundInfo(111, notification)
    }

    /*
    * Create Notification Channel
    * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            context.getString(R.string.notification_channel_id),
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "All notifications"
        channel.setShowBadge(true)
        channel.canShowBadge()
        channel.enableLights(true)
        channel.lightColor = Color.RED
        /*channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)*/
        notificationManager.createNotificationChannel(channel)
    }
}