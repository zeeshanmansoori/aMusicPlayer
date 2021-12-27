package com.zee.amusicplayer.presentation.albums_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.zee.amusicplayer.domain.model.AlbumItem

@Composable
fun SingleAlbumItem(modifier: Modifier = Modifier, albumItem: AlbumItem) {
    Column(
        modifier = modifier.padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.LightGray)
                .padding(30.dp),
            painter = painterResource(id = albumItem.albumIcon ?: R.drawable.ic_album),
            contentDescription = null
        )

        Text(
            text = albumItem.albumName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1
        )

        Text(
            text = "<unknown>",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2.copy(color = Color.Gray)
        )


    }

}

@Preview
@Composable
fun SingleSongItemPreview() {

    SingleAlbumItem(albumItem = AlbumItem("testing"))
}