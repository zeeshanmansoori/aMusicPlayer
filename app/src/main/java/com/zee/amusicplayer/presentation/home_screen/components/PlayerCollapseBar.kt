package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.exo_player.isPlaying
import com.zee.amusicplayer.presentation.songs_screen.SongsVieModel
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MarqueeText

@Composable
fun PlayerCollapseBar(
    modifier: Modifier = Modifier,
    songsVieModel: SongsVieModel
) {
    val currentSong = songsVieModel.curPlayingSong.value
    val playbackState = songsVieModel.playbackState.value

    Row(
        modifier
            .fillMaxWidth()
            .height(Constants.toolBarHeight + 4.dp)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
        MarqueeText(
            modifier = Modifier.weight(1f),
            text = currentSong?.description?.title?.toString() ?: "No song is playing"
        )
        PlayPauseBtnWithProgressBar(playbackState?.isPlaying == true) {
            if (currentSong != null)
                songsVieModel.playOrToggleSong(currentSong.description.mediaId, true)
        }
    }
}


@Composable
fun PlayPauseBtnWithProgressBar(play: Boolean = false, playStateChange: () -> Unit = {}) {
    Box(
        Modifier
            .clip(CircleShape)
            .size(48.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(color = Color.White)
            ) {
                playStateChange()
            }
            .padding(5.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize(), progress = 1f)
        Icon(
            imageVector = if (!play) Icons.Filled.PlayArrow else Icons.Filled.Pause,
            contentDescription = null
        )
    }

}



