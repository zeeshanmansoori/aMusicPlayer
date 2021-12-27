package com.zee.amusicplayer.presentation.albums_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.domain.model.AlbumItem


@ExperimentalFoundationApi
@Composable
fun AlbumScreen() {

    val toolbarHeight = 48.dp
    val paddingStart = 16.dp
    val toolbarHeightInPixel = with(LocalDensity.current) { toolbarHeight.toPx() }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = toolbarHeight, end = paddingStart, start = paddingStart)
    ) {
        itemsIndexed(AlbumItem.tempList()) { index: Int, album: AlbumItem ->

            SingleAlbumItem(
                modifier = Modifier.padding(
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
    AlbumScreen()
}