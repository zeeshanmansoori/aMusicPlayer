package com.zee.amusicplayer.presentation.main.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.presentation.main.MainViewModel
import com.zee.amusicplayer.utils.Constants

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun PlayerCollapseBar(
    modifier: Modifier = Modifier,
    playerState: MainViewModel.PlayerState = MainViewModel.PlayerState.EMPTY,
    onPlayPauseClick: () -> Unit,
) {
    val item = playerState.item
    val duration = playerState.duration
    val progress = playerState.progress

    Row(
        modifier
            .fillMaxWidth()
            .height(Constants.toolBarHeight + 4.dp)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)

        Text(
            modifier = Modifier.basicMarquee().weight(1f),
            text = item?.mediaMetadata?.title?.toString() ?: "No song is playing"
        )
        PlayPauseBtnWithProgressBar(
            playerState.isPlaying,
            progress = if (duration != 0L && progress <= duration) (progress / duration.toFloat()) else 0f,
            onPlayPauseClick
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun PlayPauseBtnWithProgressBar(
    play: Boolean = false,
    progress: Float,
    onPlayPauseClick: () -> Unit = {},
) {

    Surface(
        modifier = Modifier.size(50.dp),
        shape = CircleShape,
        onClick = onPlayPauseClick,
        color = Color.Transparent
    ) {
        Box(
            Modifier.padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (!play) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                contentDescription = null
            )

            println("zeeshan indicator progress $progress")
            CircularProgressIndicator(modifier = Modifier.fillMaxSize(), progress = progress)

        }
    }


}



