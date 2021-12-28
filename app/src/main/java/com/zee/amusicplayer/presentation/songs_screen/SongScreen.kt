package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zee.amusicplayer.presentation.MainVieModel


@Composable
fun SongsScreen(
    viewModel: SongsVieModel = hiltViewModel(),
    sharedViewModel: MainVieModel = hiltViewModel(),
) {
    val toolbarHeight = 48.dp
    val paddingStart = 16.dp
    val songs = viewModel.allSongs.value
    val defaultStatusBarColor = MaterialTheme.colors.surface
    val lazyListState = rememberLazyListState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
            .padding(
                start = paddingStart,
                bottom = paddingStart,
                end = 20.dp
            ),
        contentPadding = PaddingValues(top = toolbarHeight),
        state = rememberLazyListState()
    ) {

        items(songs) { song ->
            SingleSongItem(song = song)
        }


    }


}


@Preview(showBackground = true)
@Composable
fun SongsScreenPreview() {
    SongsScreen()
}