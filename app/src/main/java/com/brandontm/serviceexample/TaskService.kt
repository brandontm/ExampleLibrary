package com.brandontm.serviceexample

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import android.R
import android.app.*
import android.os.Build
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.graphics.Color


class TaskService : IntentService("TaskService") {
    val CHANNEL_ID = "channel_01"
    val CHANNEL_NAME = "Default"
    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val androidChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            androidChannel.enableLights(true)
            androidChannel.enableVibration(false)
            androidChannel.lightColor = Color.GREEN
            androidChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                androidChannel
            )
        }

        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TASK")
            .setContentText("In progress")
            .setAutoCancel(true)
        val notification = builder.build()

        startForeground(1, notification)
        try {
            Thread.sleep(5000)
            Log.i("TASKINTENTSERVICE", "5 seconds elapsed")
            Toast.makeText(applicationContext, "5 seconds elapsed", Toast.LENGTH_LONG).show()
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}
