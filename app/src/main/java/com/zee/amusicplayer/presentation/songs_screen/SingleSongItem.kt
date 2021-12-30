package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.common.MusicImage
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.utils.Constants

@Composable
fun SingleSongItem(modifier: Modifier = Modifier,song: SongItem) {
    Row(
        modifier = modifier
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        MusicImage(modifier = Modifier.size(40.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            Text(
                text = song.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = song.albumName ?: "<unknown>",
                maxLines = 1,
                style = MaterialTheme.typography.body2
            )
        }

        IconButton(modifier = Modifier
            .size(22.dp)
            .padding(start = 5.dp), onClick = { }) {
            Icon(painter = painterResource(id = R.drawable.ic_more_vert), contentDescription = null)
        }


    }

}
