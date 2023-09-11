package com.zee.amusicplayer.presentation.player_screen.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.zee.amusicplayer.common.MusicIconButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun PlayerControllerBar(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    onPlayingStateChange: (Boolean) -> Unit = {},
    playNext: () -> Unit={},
    playPrevious: () -> Unit={}
) {

    var isButtonTouchMode by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val scaleFactor = animateFloatAsState(targetValue = if (isButtonTouchMode) .951f else 1.2f)
    val fabColor = animateColorAsState(
        targetValue = if (isButtonTouchMode) MaterialTheme.colors.primary.copy(alpha = .8f) else MaterialTheme.colors.primary
    )
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        MusicIconButton(imageVector = Icons.Filled.SkipPrevious, onCLick = playPrevious)
        FloatingActionButton(
            onClick = {

                onPlayingStateChange(!isPlaying)
                scope.launch {
                    isButtonTouchMode = true
                    delay(100)
                    isButtonTouchMode = false
                }


            },
            modifier = Modifier
                .scale(scaleFactor.value),
            backgroundColor = fabColor.value,
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        ) {
            Icon(
                imageVector =
                if (!isPlaying) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                contentDescription = null
            )
        }


        MusicIconButton(imageVector = Icons.Filled.SkipNext, onCLick = playNext)

    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview
@Composable
fun PlayerControllerBarPreview() {
    PlayerControllerBar()
}


