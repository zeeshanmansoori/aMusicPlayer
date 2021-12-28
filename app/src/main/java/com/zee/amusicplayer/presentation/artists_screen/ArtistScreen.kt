package com.zee.amusicplayer.presentation.artists_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zee.amusicplayer.domain.model.ArtistItem
import com.zee.amusicplayer.presentation.artists_screen.component.SingleArtistItem
import com.zee.amusicplayer.utils.Constants


@ExperimentalFoundationApi
@Composable
fun ArtistScreen(viewModel: ArtistVieModel = hiltViewModel()) {
    val artists = viewModel.allArtist.value

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = Constants.toolBarHeight,
        )
    ) {
        itemsIndexed(artists) { index: Int, album: ArtistItem ->

            SingleArtistItem(
                modifier = Modifier

                    .padding(
                        start = if (index % 2 != 0) 3.dp else Constants.paddingStart.div(2),
                        end = if (index % 2 == 0) 3.dp else Constants.paddingStart.div(2),
                        top = 4.dp
                    )
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))
                    .clickable {

                    }
                    .padding(vertical = 15.dp, horizontal = Constants.paddingStart.div(2)),
                artist = album
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ArtistScreenPreview() {

}