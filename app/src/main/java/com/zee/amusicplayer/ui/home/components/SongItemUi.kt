package com.zee.amusicplayer.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.zee.amusicplayer.domain.model.Song
import com.zee.amusicplayer.ui.common.MusicImage
import com.zee.amusicplayer.utils.Constants

@Composable
fun SongItemUi(
    modifier: Modifier = Modifier,
    song: Song,
    showEqualizer: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MusicImage(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(Constants.rectanglesCorner))
                .background(color = Color.LightGray),
            artUri = song.artUri,
        )

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
                text = song.artistName,
                maxLines = 1,
                style = MaterialTheme.typography.body2
            )
        }

        if (showEqualizer)
            EqualizerLoader()

        IconButton(modifier = Modifier
            .size(22.dp)
            .padding(start = 5.dp), onClick = { }) {
            Icon(painter = painterResource(id = R.drawable.ic_more_vert), contentDescription = null)
        }


    }

}

