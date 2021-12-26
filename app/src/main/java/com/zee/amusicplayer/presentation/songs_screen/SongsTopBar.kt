package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.ui.theme.IconTintColor
import kotlin.math.roundToInt

@Composable
fun SongTopBar(
    modifier: Modifier = Modifier,
    offset: Float = 0f,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(0, offset.roundToInt()) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_serch),
                contentDescription = "search",
                tint = IconTintColor
            )

        }



        Text(
            text = "Songs",
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            letterSpacing = 1.sp
        )

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = "settings", tint = IconTintColor
            )
        }
    }
}
