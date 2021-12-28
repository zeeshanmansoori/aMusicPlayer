package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zee.amusicplayer.utils.Constants


@Composable
fun SongsScreen(
    viewModel: SongsVieModel = hiltViewModel()
) {
    val songs = viewModel.allSongs.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(top = Constants.toolBarHeight),
        state = rememberLazyListState()
    ) {

        items(songs) { song ->
            SingleSongItem(modifier = Modifier
                .clip(RoundedCornerShape(Constants.rectanglesCorner))
                .clickable { }
                .padding(
                    end = Constants.paddingStart.plus(4.dp),
                    start = Constants.paddingStart
                ), song = song
            )
        }


    }


}


@Preview(showBackground = true)
@Composable
fun SongsScreenPreview() {
    SongsScreen()
}