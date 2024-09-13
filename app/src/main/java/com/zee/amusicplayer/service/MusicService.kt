package com.zee.amusicplayer.service

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSourceBitmapLoader
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CacheBitmapLoader
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MediaItemHelper
import com.zee.amusicplayer.presentation.MainActivity
import com.zee.amusicplayer.worker.FetchMediaWorker
import com.zee.amusicplayer.worker.SaveMetaDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

@UnstableApi
class MusicService : MediaLibraryService() {

  private lateinit var player: ExoPlayer
  private lateinit var mediaLibrarySession: MediaLibrarySession
  private lateinit var librarySessionCallback: MediaLibrarySessionCallback
  private val workManager by lazy { WorkManager.getInstance(this) }
  private val fetchWorkerRequestId = UUID.randomUUID()
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


  init {
    scope.launch {
      workManager.getWorkInfoByIdFlow(fetchWorkerRequestId).collectLatest { workInfo ->
        workInfo ?: return@collectLatest
        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
          val parentId = workInfo.outputData.getString(Constants.PARENT_ID_KEY) ?: ""
          val outPutCount = workInfo.outputData.getInt(Constants.ITEM_COUNT_KEY, 0)
          mediaLibrarySession.notifyChildrenChanged(parentId, outPutCount, null)
        }
      }
    }
  }


  companion object {
    private const val SEARCH_QUERY_PREFIX_COMPAT = "androidx://media3-session/playFromSearch"
    private const val SEARCH_QUERY_PREFIX = "androidx://media3-session/setMediaUri"
    const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON =
      "android.media3.session.demo.SHUFFLE_ON"
    const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF =
      "android.media3.session.demo.SHUFFLE_OFF"
  }

  override fun onCreate() {
    super.onCreate()
    initializeSessionAndPlayer()
    setListener(MediaSessionServiceListener(this))
    scheduleFetchTask()
  }

  private fun initializeSessionAndPlayer() {
    player = ExoPlayer.Builder(this)
      .setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true)
      .build()

    librarySessionCallback = MediaLibrarySessionCallback(player)
    mediaLibrarySession =
      MediaLibrarySession.Builder(this, player, librarySessionCallback)
        .setSessionActivity(getSingleTopActivity())
//        .setCustomLayout(ImmutableList.of(librarySessionCallback.customCommands[0]))
        .setBitmapLoader(CacheBitmapLoader(DataSourceBitmapLoader(/* context= */ this)))
        .build()
  }

  private fun scheduleFetchTask() {
    val request = OneTimeWorkRequest.Builder(FetchMediaWorker::class.java)
      .setId(fetchWorkerRequestId)
      .setInputData(Data.Builder().putString("parentId", MediaItemHelper.Root.mediaId).build())
      .build()
    workManager.enqueue(request)
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
    workManager.enqueue(OneTimeWorkRequest.from(SaveMetaDataWorker::class.java))
    mediaLibrarySession.setSessionActivity(getBackStackedActivity())
    mediaLibrarySession.release()
    player.release()
    clearListener()
    scope.cancel()
    super.onDestroy()
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



}
