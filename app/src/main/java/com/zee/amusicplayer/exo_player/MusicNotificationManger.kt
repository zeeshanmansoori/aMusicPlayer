package com.zee.amusicplayer.exo_player

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import com.zee.amusicplayer.R
import com.zee.amusicplayer.utils.Constants


class MusicNotificationManger(
    private val context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener,
    private val newSongCallBack: () -> Unit
) {


    private val mediaControllerCompat = MediaControllerCompat(context, sessionToken)
    private val notificationManger: PlayerNotificationManager =
        PlayerNotificationManager.Builder(context, Constants.NOTIFICATION_ID, Constants.CHANNEL_ID)
            .apply {


                setMediaDescriptionAdapter(DescriptionAdapter(mediaController = mediaControllerCompat))
                setNotificationListener(notificationListener)

            }
            .build()
            .apply {

                NotificationUtil.createNotificationChannel(
                    context,
                    Constants.CHANNEL_ID,
                    R.string.channel_name,
                    R.string.channel_description,
                    NotificationUtil.IMPORTANCE_HIGH
                )
                setSmallIcon(R.drawable.ic_songs)
                setMediaSessionToken(sessionToken)

            }


    fun showNotification(player: Player) {
        notificationManger.setPlayer(player)
    }

    private inner class DescriptionAdapter(
        private val mediaController: MediaControllerCompat
    ) : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            return mediaController.metadata.description.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return mediaController.sessionActivity
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            return mediaController.metadata.description.title.toString()
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            //TODO("need to update")
            return null
        }

    }

}