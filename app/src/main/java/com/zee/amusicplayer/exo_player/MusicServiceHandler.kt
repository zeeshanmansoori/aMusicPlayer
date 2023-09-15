package com.zee.amusicplayer.exo_player

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class MusicServiceHandler @Inject constructor(
    private val coroutineScope: CoroutineScope,
    context: Context,
    sessionToken: SessionToken,
) : Player.Listener {

    private val browserFuture = MediaBrowser.Builder(context, sessionToken).buildAsync()
    private val browser get() = if (browserFuture.isDone) browserFuture.get() else null

    private val commands = mutableListOf<() -> Unit>()

    private var onBrowserInitListener: ((MediaBrowser) -> Unit)? = null

    init {

        browserFuture.addListener(
            {
                val controller = browserFuture.get()
                onBrowserInitListener?.invoke(controller)
                controller.addListener(this@MusicServiceHandler)
            },
            ContextCompat.getMainExecutor(context)
        )
    }

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Initial)
    val playerState = _playerState.asStateFlow()

    private val _currentMediaItem = MutableStateFlow<MediaItem?>(null)
    val currentMediaItem = _currentMediaItem.asStateFlow()

    private var job: Job? = null


    fun setOnBrowserInitListener(listener: (MediaBrowser) -> Unit) {
        onBrowserInitListener = listener
    }

    private fun addMediaItems(mediaItems: List<MediaItem>) {
        val command = {
            browser?.run {
                clearMediaItems()
                //setMediaItems(mediaItems)
                setMediaItems(
                    mediaItems,
                    /* startIndex= */ 0,
                    /* startPositionMs= */ C.TIME_UNSET
                )
                prepare()
                playWhenReady = true
            }

            Unit
        }

        if (browser != null) {
            command.invoke()
        } else commands.add(command)

    }


    fun onPlayerEvents(
        playerEvent: PlayerEvent
    ) = browser?.run {
        println("zeeshan onPlayerEvents event $playerEvent")
        when (playerEvent) {
            PlayerEvent.PlayPause -> playPausePlayer()
            PlayerEvent.PlayPrevious -> seekToPreviousMediaItem()
            PlayerEvent.PlayNext -> seekToNextMediaItem()
            PlayerEvent.Stop -> stop()
            is PlayerEvent.PlayAtIndex -> playSongAt(playerEvent.mediaItemIndex)
            is PlayerEvent.SeekTo -> seekTo(playerEvent.position)
            else -> {

            }
        }
    }

    private fun playSongAt(mediaItemIndex: Int) = browser?.run {
        println("zeeshan playSongAt mediaItemIndex $mediaItemIndex currentMediaItemIndex $currentMediaItemIndex")
        if (mediaItemIndex == currentMediaItemIndex) {
            if (!isPlaying)
                playPausePlayer()
            return@run
        }
        seekToDefaultPosition(mediaItemIndex)
    }

    private fun playPausePlayer() = browser?.run {
        if (isPlaying) pause() else play()
    }

    private suspend fun startProgressUpdate() {
        while (true) {
            browser?.run {
                _playerState.value = PlayerState.Playing(
                    isPlaying = isPlaying,
                    progress = currentPosition,
                    duration = duration,
                )

            }
            delay(1000)
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        browser?.run {

            when (playbackState) {
                ExoPlayer.STATE_BUFFERING -> _playerState.value =
                    PlayerState.Buffering(currentPosition)

                ExoPlayer.STATE_READY -> _playerState.value = PlayerState.Ready
                else -> {

                }
            }
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        browser?.run {
            _playerState.value = PlayerState.Playing(
                isPlaying = isPlaying,
                progress = currentPosition,
                duration = duration,
            )
        }
        if (isPlaying) job = coroutineScope.launch(Dispatchers.Main) {
            startProgressUpdate()
        }
        else job?.cancel()

    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        browser?.run {
            _currentMediaItem.value = currentMediaItem
        }
    }

    sealed class PlayerEvent {
        data object PlayPause : PlayerEvent()
        data object PlayPrevious : PlayerEvent()
        data object PlayNext : PlayerEvent()
        data object Stop : PlayerEvent()
        data class PlayAtIndex(val mediaItemIndex: Int) : PlayerEvent()
        data class SeekTo(val position: Long) : PlayerEvent()


    }

    sealed class PlayerState {
        open val progress: Long = 0
        open val duration: Long = 0

        data object Initial : PlayerState()
        data object Ready : PlayerState()
        data class Playing(
            val isPlaying: Boolean, override val progress: Long, override val duration: Long
        ) : PlayerState()

        data class Buffering(override val progress: Long) : PlayerState()


    }

}