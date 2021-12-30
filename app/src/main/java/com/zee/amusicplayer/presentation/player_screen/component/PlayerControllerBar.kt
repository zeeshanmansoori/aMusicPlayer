package com.zee.amusicplayer.presentation.player_screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zee.amusicplayer.common.MusicIconButton

@Composable
fun PlayerControllerBar(modifier: Modifier = Modifier) {

    val isPlaying = false
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        MusicIconButton(imageVector = Icons.Filled.SkipPrevious) {}
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector =
                if (isPlaying) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                contentDescription = null
            )
        }
        MusicIconButton(imageVector = Icons.Filled.SkipNext) {}

    }
}

@Preview
@Composable
fun PlayerControllerBarPreview() {
    PlayerControllerBar()
}


