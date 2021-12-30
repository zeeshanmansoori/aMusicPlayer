package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MarqueeText

@Composable
fun PlayerCollapseBar(
    modifier: Modifier = Modifier,
    songTitle: String = "this is my fav song i dont deserve you dear dash dash dadh",
    onclick: () -> Unit = {}
) {

    Row(
        modifier
            .fillMaxWidth()
            .height(Constants.toolBarHeight + 4.dp)
            .clickable { onclick() }
            .background(color = MaterialTheme.colors.surface)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
        MarqueeText(modifier = Modifier.weight(1f), text = songTitle)
        PlayPauseBtnWithProgressBar()
    }
}


@Composable
fun PlayPauseBtnWithProgressBar(play: Boolean = false) {
    Box(
        Modifier
            .clip(CircleShape)
            .size(48.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(color = Color.White)
            ) {

            }
            .padding(5.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize(), progress = 1f)
        Icon(
            imageVector = if (play) Icons.Filled.PlayArrow else Icons.Filled.Pause,
            contentDescription = null
        )
    }

}

@Preview
@Composable
fun PlayerCollapseBarPre() {
    PlayerCollapseBar()
}