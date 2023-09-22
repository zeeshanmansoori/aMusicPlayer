package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaBrowser
import com.google.common.util.concurrent.MoreExecutors
import com.zee.amusicplayer.exo_player.MusicServiceHandler
import com.zee.amusicplayer.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@UnstableApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serviceHandler: MusicServiceHandler,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems = _mediaItems.asStateFlow()

    val currentMediaItem: StateFlow<MediaItem?> = serviceHandler.currentMediaItem

    init {
        viewModelScope.launch {
            launch {
                collectPlayerState()
            }

            serviceHandler.setOnBrowserInitListener {
                it.fetchSongs()
            }

        }
    }

    private fun MediaBrowser.fetchSongs() {

        val childrenFuture = getChildren(
            Constants.AUDIO_BOOK_ID,
            /* page= */0,
            /* pageSize= */Int.MAX_VALUE,
            /* params= */null
        )
        childrenFuture.addListener({

            val children = childrenFuture.get() ?: return@addListener

            val items = children.value ?: emptyList()
            setMediaItems(items,true)
            prepare()
            _mediaItems.value = items

//            play()

        }, MoreExecutors.directExecutor())

    }


    fun seekTo(position: Long) {
        serviceHandler.onPlayerEvents(MusicServiceHandler.PlayerEvent.SeekTo(position))
    }

    fun seekToNext() {
        serviceHandler.onPlayerEvents(MusicServiceHandler.PlayerEvent.PlayNext)
    }

    fun seekToPrevious() {
        serviceHandler.onPlayerEvents(MusicServiceHandler.PlayerEvent.PlayPrevious)

    }


    @OptIn(SavedStateHandleSaveableApi::class)
    var isPlaying by savedStateHandle.saveable {
        mutableStateOf(false)
    }

    @OptIn(SavedStateHandleSaveableApi::class)
    var progress by savedStateHandle.saveable {
        mutableStateOf(0L)
    }

    @OptIn(SavedStateHandleSaveableApi::class)
    var duration by savedStateHandle.saveable {
        mutableStateOf(0L)
    }


    private suspend fun collectPlayerState() {
        serviceHandler.playerState.collectLatest { playerState ->
            when (playerState) {
                is MusicServiceHandler.PlayerState.Playing -> {
                    isPlaying = playerState.isPlaying
                    progress = playerState.progress
                }

                else -> {

                }

            }
        }
    }

    fun playOrToggleSong(mediaId: String) {
        serviceHandler.onPlayerEvents(MusicServiceHandler.PlayerEvent.PlayAtIndex(mediaId.toInt()))
    }


}
