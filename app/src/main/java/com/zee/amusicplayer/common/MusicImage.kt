package com.zee.amusicplayer.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.utils.Constants


@Composable
fun MusicImage(
    modifier: Modifier = Modifier,
    cornerSize: Dp = Constants.rectanglesCorner
) {
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(cornerSize))
            .background(color = Color.LightGray)
            .padding(10.dp),
        painter = painterResource(id = R.drawable.ic_songs),
        contentDescription = null
    )
}
