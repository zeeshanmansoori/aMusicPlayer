package com.zee.amusicplayer.exo_player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Event
import com.zee.amusicplayer.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MusicServiceConnection @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val _isConnected = mutableStateOf<Event<Resource<Boolean>>>(Event(data = Resource.Success(false)))
    val isConnected: State<Event<Resource<Boolean>>> = _isConnected

    private val _networkError = mutableStateOf<Event<Resource<Boolean>>>(Event(data = Resource.Success(false)))
    val networkError: State<Event<Resource<Boolean>>> = _networkError

    private val _playbackState = mutableStateOf<PlaybackStateCompat?>(null)
    val playbackState: State<PlaybackStateCompat?> = _playbackState

    private val _curPlayingSong = mutableStateOf<MediaMetadataCompat?>(null)
    val curPlayingSong: State<MediaMetadataCompat?> = _curPlayingSong

    lateinit var mediaController: MediaControllerCompat

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(
            context,
            MusicService::class.java
        ),
        mediaBrowserConnectionCallback,
        null
    ).apply { connect() }

    val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

    private inner class MediaBrowserConnectionCallback(
        private val context: Context
    ) : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            Log.d("MusicServiceConnection", "CONNECTED")
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaContollerCallback())
            }
            _isConnected.value = Event(Resource.Success(true))

        }

        override fun onConnectionSuspended() {
            Log.d("MusicServiceConnection", "SUSPENDED")

            _isConnected.value =(
                Event(
                    Resource.Error(
                        false,
                        "The connection was suspended",
                    )
                )
            )
        }

        override fun onConnectionFailed() {
            Log.d("MusicServiceConnection", "FAILED")

            _isConnected.value = (
                Event(
                    Resource.Error(
                        false,
                        "Couldn't connect to media browser"
                    )
                )
            )
        }
    }

    private inner class MediaContollerCallback : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            _playbackState.value = (state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            _curPlayingSong.value = (metadata)
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            super.onSessionEvent(event, extras)
            when (event) {
                Constants.NETWORK_ERROR -> _networkError.value = (
                    Event(
                        Resource.Error(
                            null,
                            "Couldn't connect to the server. Please check your internet connection."
                        )
                    )
                )
            }
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}




