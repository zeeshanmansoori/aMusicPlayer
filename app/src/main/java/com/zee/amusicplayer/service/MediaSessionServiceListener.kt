package com.zee.amusicplayer.service

import android.annotation.SuppressLint
import android.util.Log
import androidx.media3.session.MediaSessionService

@SuppressLint("UnsafeOptInUsageError")
class MediaSessionServiceListener(service: MusicService) :
    MediaSessionService.Listener {

    /**
     * This method is only required to be implemented on Android 12 or above when an attempt is made
     * by a media controller to resume playback when the {@link MediaSessionService} is in the
     * background.
     */
    override fun onForegroundServiceStartNotAllowedException() {
        Log.d("zeeshan", "onForegroundServiceStartNotAllowedException: ")
        super.onForegroundServiceStartNotAllowedException()
    }
//    @SuppressLint("MissingPermission")
//    override fun onForegroundServiceStartNotAllowedException() {
//        val notificationManagerCompat = NotificationManagerCompat.from(service)
//        service.ensureNotificationChannel(notificationManagerCompat)
//        val pendingIntent =
//            TaskStackBuilder.create(service).run {
////          addNextIntent(Intent(this@PlaybackService, MainActivity::class.java))
//                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
//            }
//        val builder =
//            NotificationCompat.Builder(service, CHANNEL_ID)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(android.R.drawable.btn_default_small)
//                .setContentTitle("content title")
//                .setStyle(
//                    NotificationCompat.BigTextStyle().bigText("big text")
//                )
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
//    }
}
