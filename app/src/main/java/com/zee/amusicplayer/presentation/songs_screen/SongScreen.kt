package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zee.amusicplayer.domain.model.SongItem


@Composable
fun SongsScreen(viewModel: SongsVieModel = hiltViewModel()) {
    val toolbarHeight = 48.dp
    val paddingStart = 16.dp
    val toolbarHeightInPixel = with(LocalDensity.current) { toolbarHeight.toPx() }
    val songs = viewModel.allSongs.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = paddingStart,
                top = paddingStart,
                bottom = paddingStart,
                end = 20.dp
            ),
        contentPadding = PaddingValues(top = toolbarHeight)
    ) {

        items(songs) {
            song ->
            SingleSongItem(song = song)
        }


    }

}


@Preview(showBackground = true)
@Composable
fun SongsScreenPreview() {
    SongsScreen()
}