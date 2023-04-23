package com.zee.amusicplayer.presentation.songs_screen

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.exo_player.*
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SongsVieModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {
    private val _mediaItems =
        mutableStateOf<Resource<List<SongItem>>>(Resource.Loading(data = null))
    val mediaItems: State<Resource<List<SongItem>>> = _mediaItems

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val playbackState = musicServiceConnection.playbackState

    val curPlayingSong = musicServiceConnection.curPlayingSong

    private val _curSongDuration = mutableStateOf(0L)
    val curSongDuration: State<Long> = _curSongDuration

    private val _curPlayerPosition = mutableStateOf(0L)
    val curPlayerPosition: State<Long> = _curPlayerPosition


    init {
        initThings()
    }


    private fun initThings() {
        musicServiceConnection.subscribe(Constants.MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val items = children.map {
                        SongItem(
                            id = it.mediaId?.toLong() ?: 0L,
                            title = it.description.title.toString(),
                            albumName = it.description.subtitle.toString(),
                            albumUri = it.description.iconUri?.toString(),
                            contentUri = it.description.mediaUri?.toString(),
                        )
                    }
                    _mediaItems.value = Resource.Success(data = items)
                }
            })

        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while (true) {

                val pos = playbackState.value?.currentPlaybackPosition
                val currentSongDuration = MusicService.currentSongDuration
                if (curPlayerPosition.value != pos) {
                    _curPlayerPosition.value = pos ?: 0
                    _curSongDuration.value = currentSongDuration
                }
                delay(Constants.UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicServiceConnection.transportControls.seekTo(pos)
    }


    fun playOrToggleSong(mediaItem: SongItem, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.id.toString() == curPlayingSong.value?.getString(
                MediaMetadataCompat.METADATA_KEY_MEDIA_ID
            )
        ) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.id.toString(), null)
        }
    }

    fun playOrToggleSong(mediaId: String?, toggle: Boolean = false) {
        if (mediaId == null) return
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaId == curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaId, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(Constants.MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}



