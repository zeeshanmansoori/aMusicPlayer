package com.zee.amusicplayer.presentation.albums_screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zee.amusicplayer.domain.model.AlbumItem
import com.zee.amusicplayer.presentation.albums_screen.component.SingleAlbumItem
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Constants.toolBarHeight
import com.zee.amusicplayer.utils.showToast


@ExperimentalFoundationApi
@Composable
fun AlbumScreen(viewModel: AlbumVieModel) {

    val albums = viewModel.allAlbums.collectAsState().value
    val context = LocalContext.current
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = toolBarHeight,
            start = Constants.paddingStart,
            end = Constants.paddingStart
        )
    ) {
        itemsIndexed(albums) { index: Int, album: AlbumItem ->

            SingleAlbumItem(
                modifier = Modifier
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))
                    .clickable {
                        showToast(
                            context = context,
                            "this feature is not available yet."
                        )
                    }
                    .padding(
                        start = if (index % 2 == 0) 0.dp else 4.dp,
                        end = if (index % 2 == 0) 4.dp else 0.dp
                    ),
                albumItem = album
            )
        }

    }
}


@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun SongsScreenPreview() {
    AlbumScreen(hiltViewModel())
}