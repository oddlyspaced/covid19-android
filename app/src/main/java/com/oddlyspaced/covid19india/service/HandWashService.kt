package com.oddlyspaced.covid19india.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.activity.SplashActivity


class HandWashService: Service() {

    private val tag = "HANDWASH_SERVICE"
    val actionStartForegroundService = "ACTION_START_FOREGROUND_SERVICE"
    val actionStopForegroundService = "ACTION_STOP_FOREGROUND_SERVICE"
    private val notificationChannelId = "10002"

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(tag, "Service started!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            when(intent.action) {
                actionStartForegroundService -> {
                    startForegroundService()
                    Toast.makeText(applicationContext, "Service Started", Toast.LENGTH_LONG).show()
                }
                actionStopForegroundService -> {
                    stopForegroundService()
                    Toast.makeText(applicationContext, "Service Stopped", Toast.LENGTH_LONG).show()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.app_name)
        val descriptionText = getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_MIN
        val channel = NotificationChannel(notificationChannelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification() {
        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hand Wash service is running")
            .setPriority(NotificationCompat.PRIORITY_MIN)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setWhen(System.currentTimeMillis())

        val notification = builder.build()
        startForeground(2, notification)
    }

    private fun handwashNotify() {
        val builder = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Time to wash your hands!")
            .setPriority(NotificationCompat.PRIORITY_MIN)
            // Set the intent that will fire when the user taps the notification
            .setAutoCancel(false)
            .setWhen(System.currentTimeMillis())

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify((1..100).random(), builder.build())
        }
    }

    private fun startForegroundService() {
        Log.d(tag, "Starting service...")
        createNotificationChannel()
        createNotification()
        notifyHandsHandler()
    }

    private fun notifyHandsHandler() {
        Handler().postDelayed({
            handwashNotify()
            notifyHandsHandler()
        }, 60*60*1000) // every hour
    }

    private fun stopForegroundService() {
        Log.d(tag, "Stopping service...")
        stopForeground(true)
        stopSelf()
    }

}