package com.zee.amusicplayer.exo_player.callbacks

import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.zee.amusicplayer.exo_player.MusicService
import com.zee.amusicplayer.utils.showToast

class MusicPlayerEventListener(private val musicService: MusicService) :
    Player.Listener {


    var playWhenReady = false

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        this.playWhenReady = playWhenReady
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_READY && !playWhenReady) {
            musicService.stopForeground(false)
        }

    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        showToast(musicService, "an unknown error occurred")

    }
}