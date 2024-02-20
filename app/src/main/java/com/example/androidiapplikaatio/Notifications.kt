package com.example.androidiapplikaatio

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

class Notifications: Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "channelId",
            "channelName", NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
@Composable
fun NotificationsScreen(
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val notificationLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                hasNotificationPermission = isGranted
            }
        )
        Button(onClick = {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }) {
            Text(text = "Request Notification Permission")
        }

        Button(onClick = {
            if (hasNotificationPermission) {
                val serviceIntent = Intent(context, LuxSensorService::class.java)
                ContextCompat.startForegroundService(context, serviceIntent)
                Log.d("Notifications", "Service started")
            } else {
                // Request missing permissions
            }
        }) {
            Text(text = "Start Sensor Monitoring")
        }

        Button(onClick = {
            val serviceIntent = Intent(context, LuxSensorService::class.java)
            context.stopService(serviceIntent)
        }) {
            Text(text = "Stop Sensor Monitoring")
        }
    }
}

