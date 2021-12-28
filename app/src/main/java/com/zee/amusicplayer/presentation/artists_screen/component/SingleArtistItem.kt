package com.zee.amusicplayer.presentation.artists_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.domain.model.ArtistItem

@Composable
fun SingleArtistItem(modifier: Modifier = Modifier, artist: ArtistItem) {


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(color = Color.LightGray)
                .padding(30.dp),
            painter = painterResource(id = R.drawable.ic_artist),
            contentDescription = null
        )

        Text(
            text = artist.artistName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1
        )


    }
}

@Preview
@Composable
fun SAP() {
    SingleArtistItem(artist = ArtistItem(1, "fed", emptyList()))
}

