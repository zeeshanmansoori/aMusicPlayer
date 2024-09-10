package com.zee.amusicplayer.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.domain.model.Song
import com.zee.amusicplayer.presentation.home.components.HomeScreenActionBar
import com.zee.amusicplayer.presentation.home.components.SongItemUi
import com.zee.amusicplayer.utils.Constants


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    songs: List<Song> = emptyList(),
    currentSong: Song? = null,
    onItemClick: (index: Int) -> Unit = {}
) {


    if (songs.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {


        HomeScreenActionBar(
            modifier = Modifier
                .padding(bottom = 4.dp),
        )

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = Constants.SIDE_PADDING),
            state = rememberLazyListState(),
        ) {

            itemsIndexed(songs, key = { _, b ->
                b.id
            }) { index, item ->
                SongItemUi(
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(Constants.rectanglesCorner))
                        .clickable { onItemClick(index) }
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    song = item,
                    showEqualizer = item.id == currentSong?.id,
                )
            }
        }

        }

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}