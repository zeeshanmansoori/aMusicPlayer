package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.domain.data.SongItem


@Composable
fun SongsScreen() {
    val ls = listOf<SongItem>(
        SongItem("imran hai mera naaam oye oye oye 1", null, null),
        SongItem("imran hai mera naaam oye oye oye 2", null, null),
        SongItem("imran hai mera naaam oye oye oye 3", null, null),
        SongItem("imran hai mera naaam oye oye oye 4", null, null),
    )

    Box(Modifier.fillMaxSize()) {
        val toolbarHeight = 48.dp
        val paddingStart = 16.dp
        val toolbarHeightInPixel = with(LocalDensity.current) { toolbarHeight.toPx() }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = paddingStart, top = paddingStart, bottom = paddingStart, end = 2.dp),
            contentPadding = PaddingValues(top = toolbarHeight)
        ) {
            items(ls) { song ->
                SingleSongItem(song = song)
            }
        }
        SongTopBar(
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 2.dp, vertical = 1.dp), offset = 0f
        )
    }

}


@Preview(showBackground = true)
@Composable
fun SongsScreenPreview() {
    SongsScreen()
}