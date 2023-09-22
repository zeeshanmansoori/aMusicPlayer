package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.presentation.songs_screen.HomeViewModel
import com.zee.amusicplayer.presentation.songs_screen.SongsViewModel
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MarqueeText

@ExperimentalMaterialApi
@Composable
fun PlayerCollapseBar(
    modifier: Modifier = Modifier,
    songsVieModel: HomeViewModel
) {
    val currentSong = songsVieModel.currentMediaItem.value
    val currentSongDuration = songsVieModel.duration
    val curPosition = songsVieModel.progress


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
            text = currentSong?.mediaMetadata?.title?.toString() ?: "No song is playing"
        )
        PlayPauseBtnWithProgressBar(
//            playbackState?.isPlaying == true,
            false,
            progress = if (currentSongDuration != 0L && curPosition <= currentSongDuration)
                curPosition.toFloat() / currentSongDuration else 0f
        ) {
//            if (currentSong != null)
//                songsVieModel.plcayOrToggleSong(currentSong, true)
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun PlayPauseBtnWithProgressBar(
    play: Boolean = false,
    progress: Float,
    playStateChange: () -> Unit = {},
) {

    Surface(modifier = Modifier.size(50.dp), shape = CircleShape, onClick = playStateChange, color = Color.Transparent) {
        Box(
            Modifier.padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (!play) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                contentDescription = null
            )

            CircularProgressIndicator(modifier = Modifier.fillMaxSize(), progress = progress)

        }
    }


}



