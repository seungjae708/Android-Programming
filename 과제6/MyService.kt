package com.example.s2071321

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.io.File
import java.io.IOException

class MyService : Service() {

    private val binder = LocalBinder()
    var initValue: Int = 0
        private set

    private val channelID = "service_channel"

    inner class LocalBinder : Binder() {
        fun getService() = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelID, "Service Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notification channel for service."
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, channelID)
        .setContentTitle("Foreground Service")
        .setContentText("Foreground Service running")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setOnlyAlertOnce(true)
        .build()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(10, createNotification())
        val initValue = intent?.getIntExtra("init", 0) ?: 0
        saveInitValueToFile(initValue)
        this.initValue = initValue

        return START_STICKY
    }

    private fun saveInitValueToFile(value: Int) {
        try {
            val file = File(getExternalFilesDir(null), "init_value.txt")
            file.writeText(value.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadInitValueFromFile(): Int {
        val file = File(getExternalFilesDir(null), "init_value.txt")
        return if (file.exists()) {
            file.readText().toIntOrNull() ?: 0
        } else {
            0
        }
    }
}