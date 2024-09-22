package com.zee.amusicplayer.presentation.artists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.domain.model.Artist
import com.zee.amusicplayer.domain.useCase.artist.ArtistsUseCase
import com.zee.amusicplayer.presentation.artists.components.ArtistItem
import com.zee.amusicplayer.utils.Constants


@Composable
fun ArtistScreen(userCase: ArtistsUseCase) {

    val artists = userCase.allArtists.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(artists.value) { index: Int, album: Artist ->

            ArtistItem(
                modifier = Modifier
                    .padding(
                        start = if (index % 2 != 0) 3.dp else Constants.SIDE_PADDING.div(2),
                        end = if (index % 2 == 0) 3.dp else Constants.SIDE_PADDING.div(2),
                        top = 4.dp
                    )
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))
                    .clickable {

                    }
                    .padding(vertical = 15.dp, horizontal = Constants.SIDE_PADDING.div(2)),
                artist = album
            )
        }

    }
}

