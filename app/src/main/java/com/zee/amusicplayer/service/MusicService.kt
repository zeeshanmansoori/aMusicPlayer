package com.zee.amusicplayer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSourceBitmapLoader
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CacheBitmapLoader
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession.ControllerInfo
import com.zee.amusicplayer.data.dataSource.AudioOfflineDataSource
import com.zee.amusicplayer.data.repository.SongRepositoryImpl
import com.zee.amusicplayer.domain.repository.ISongRepository
import com.zee.amusicplayer.presentation.MainActivity

@UnstableApi
class MusicService : MediaLibraryService() {

  private lateinit var player: ExoPlayer
  private lateinit var mediaLibrarySession: MediaLibrarySession
  private lateinit var librarySessionCallback: MediaLibrarySessionCallback
  private val repository: ISongRepository by lazy {
    SongRepositoryImpl(AudioOfflineDataSource(this))
  }

  companion object {
    private const val SEARCH_QUERY_PREFIX_COMPAT = "androidx://media3-session/playFromSearch"
    private const val SEARCH_QUERY_PREFIX = "androidx://media3-session/setMediaUri"
    const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON =
      "android.media3.session.demo.SHUFFLE_ON"
    const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF =
      "android.media3.session.demo.SHUFFLE_OFF"
    const val NOTIFICATION_ID = 123
    const val CHANNEL_ID = "demo_session_notification_channel_id"
  }

  override fun onCreate() {
    super.onCreate()
    initializeSessionAndPlayer()
    setListener(MediaSessionServiceListener(this))
  }

  override fun onGetSession(controllerInfo: ControllerInfo): MediaLibrarySession {
    return mediaLibrarySession
  }

  override fun onTaskRemoved(rootIntent: Intent?) {
    if (!player.playWhenReady || player.mediaItemCount == 0) {
      stopSelf()
    }
  }

  override fun onDestroy() {
    mediaLibrarySession.setSessionActivity(getBackStackedActivity())
    mediaLibrarySession.release()
    player.release()
    clearListener()
    super.onDestroy()
  }

  private fun initializeSessionAndPlayer() {
    player = ExoPlayer.Builder(this)
      .setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true)
      .build()
    librarySessionCallback = MediaLibrarySessionCallback(player, repository =repository)
    mediaLibrarySession =
      MediaLibrarySession.Builder(this, player, librarySessionCallback)
        .setSessionActivity(getSingleTopActivity())
//        .setCustomLayout(ImmutableList.of(librarySessionCallback.customCommands[0]))
        .setBitmapLoader(CacheBitmapLoader(DataSourceBitmapLoader(/* context= */ this)))
        .build()
  }

  private fun getSingleTopActivity(): PendingIntent {
    return getActivity(
      this,
      0,
      Intent(this, MainActivity::class.java),
      FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
    )
  }

  private fun getBackStackedActivity(): PendingIntent {
    return TaskStackBuilder.create(this).run {
//      addNextIntent(Intent(this@PlaybackService, MainActivity::class.java))
      addNextIntent(Intent(this@MusicService, MainActivity::class.java))
      getPendingIntent(0, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
    }
  }

  fun ensureNotificationChannel(notificationManagerCompat: NotificationManagerCompat) {
    if (Util.SDK_INT < 26 || notificationManagerCompat.getNotificationChannel(CHANNEL_ID) != null) {
      return
    }

    val channel =
      NotificationChannel(
        CHANNEL_ID,
        "channel_name_zee",
        NotificationManager.IMPORTANCE_DEFAULT
      )
    notificationManagerCompat.createNotificationChannel(channel)
  }
}
