package com.zee.amusicplayer.worker

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.Util
import com.zee.amusicplayer.R

object WorkerUtils {

    private const val CHANNEL_NAME: String = "Music"
    private const val CHANNEL_DESCRIPTION = "Channel Description"
    private const val CHANNEL_ID = "Music_Channel_ID"

    @SuppressLint("UnsafeOptInUsageError")
    fun ensureNotificationChannel(notificationManager: NotificationManagerCompat) {
        if (Util.SDK_INT < 26 || notificationManager.getNotificationChannel(CHANNEL_ID) != null) {
            return
        }

        val name = CHANNEL_NAME
        val description = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        notificationManager.createNotificationChannel(channel)
    }

    fun makeStatusNotification(
        notificationId: Int,
        contentTitle: String,
        message: String,
        context: Context,
        pendingIntent: PendingIntent? = null,
        bitmap: Bitmap? = null
    ) {

        // Add the channel
        val notificationManager = NotificationManagerCompat.from(context)
        ensureNotificationChannel(notificationManager)

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(contentTitle)
            .setContentText(message)
            .setLargeIcon(bitmap)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setVibrate(LongArray(0))

        // Show the notification
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "notification permission is missing", Toast.LENGTH_SHORT).show()
            return
        }

        notificationManager.notify(notificationId, builder.build())
    }

    fun dismissNotification(notificationId: Int, context: Context) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }

}