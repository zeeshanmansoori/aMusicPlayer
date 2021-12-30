package com.zee.amusicplayer.exo_player

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.zee.amusicplayer.exo_player.callbacks.MusicPlayerBackPreparer
import com.zee.amusicplayer.exo_player.callbacks.MusicPlayerEventListener
import com.zee.amusicplayer.exo_player.callbacks.MusicPlayerNotificationListener
import com.zee.amusicplayer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

private const val SERVICE_TAG: String = "MusicService"

@AndroidEntryPoint
class MusicService : MediaBrowserServiceCompat() {


    @Inject
    lateinit var dataSourceFactory: DefaultDataSource.Factory

    @Inject
    lateinit var musicSource: LocalMusicSource

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector
    var isForegroundService = false
    private var curPlayingSong: MediaMetadataCompat? = null
    private var isPlayerInitialized = false

    private lateinit var musicNotificationManger: MusicNotificationManger
    private lateinit var musicPlayerEventListener: MusicPlayerEventListener

    companion object {
        var currentSongDuration = 0L
            private set

    }

    override fun onCreate() {
        super.onCreate()
        serviceScope.launch {
            musicSource.load()
        }
        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, 0)
        }
        mediaSession = MediaSessionCompat(this, SERVICE_TAG)
            .apply {
                setSessionActivity(activityIntent)
                isActive = true
            }

        sessionToken = mediaSession.sessionToken
        musicNotificationManger = MusicNotificationManger(
            this,
            mediaSession.sessionToken,
            MusicPlayerNotificationListener(this)
        ) {

            currentSongDuration = exoPlayer.duration
        }

        val musicPlayerBackPreparer = MusicPlayerBackPreparer(musicSource = musicSource) {
            curPlayingSong = it
            preparePlayer(musicSource.songList, it, true)
        }

        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlaybackPreparer(musicPlayerBackPreparer)
        mediaSessionConnector.setQueueNavigator(MusicQueueNavigator())
        mediaSessionConnector.setPlayer(exoPlayer)
        musicPlayerEventListener = MusicPlayerEventListener(this)
        exoPlayer.addListener(musicPlayerEventListener)
        musicNotificationManger.showNotification(player = exoPlayer)

    }

    private fun preparePlayer(
        songs: List<MediaMetadataCompat>,
        itemToPlay: MediaMetadataCompat?,
        playNow: Boolean
    ) {
        val curSongIndex = if (curPlayingSong == null) 0 else songs.indexOf(itemToPlay)
        exoPlayer.setMediaSource(musicSource.asMediaSource(dataSourceFactory))
        exoPlayer.prepare()
        exoPlayer.seekTo(curSongIndex, 0L)
        exoPlayer.playWhenReady = playNow
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(Constants.MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        when (parentId) {
            Constants.MEDIA_ROOT_ID -> {
                val resultsSent = musicSource.whenReady { isInitialized ->
                    if (isInitialized) {
                        result.sendResult(musicSource.asMediaItems())
                        if (!isPlayerInitialized && musicSource.songList.isNotEmpty()) {
                            preparePlayer(musicSource.songList, musicSource.songList[0], false)
                            isPlayerInitialized = true
                        }

                    } else {
                        mediaSession.sendSessionEvent(Constants.NETWORK_ERROR,null)
                        result.sendResult(null)
                    }

                }

                if (!resultsSent) {
                    result.detach()
                }

            }
        }
    }


    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        exoPlayer.removeListener(musicPlayerEventListener)
        exoPlayer.release()
    }


    private inner class MusicQueueNavigator : TimelineQueueNavigator(mediaSession) {
        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
            return musicSource.songList[windowIndex].description
        }

    }

}