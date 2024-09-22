package com.zee.amusicplayer.presentation.album

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.domain.model.Album
import com.zee.amusicplayer.domain.useCase.album.AlbumUseCase
import com.zee.amusicplayer.presentation.album.component.SingleAlbumUi
import com.zee.amusicplayer.utils.Constants


@Composable
fun AlbumScreen(useCase: AlbumUseCase) {

    val albums by useCase.allAlbums.collectAsState()
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = Constants.SIDE_PADDING)
    ) {

        itemsIndexed(albums) { index: Int, album: Album ->

            SingleAlbumUi(
                modifier = Modifier
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))
                    .clickable {
//                        showToast(
//                            context = context,
//                            "this feature is not available yet."
//                        )
                    }
                    .padding(
                        start = if (index % 2 == 0) 0.dp else 4.dp,
                        end = if (index % 2 == 0) 4.dp else 0.dp
                    ),
                album = album
            )
        }

    }
}