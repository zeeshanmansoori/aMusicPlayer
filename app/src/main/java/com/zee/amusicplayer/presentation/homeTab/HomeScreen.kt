package com.zee.amusicplayer.presentation.homeTab

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
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import com.zee.amusicplayer.presentation.homeTab.components.HomeScreenActionBar
import com.zee.amusicplayer.presentation.homeTab.components.SongItemUi
import com.zee.amusicplayer.presentation.main.MainViewModel
import com.zee.amusicplayer.utils.Constants


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: MainViewModel.PlayerScreenState = MainViewModel.PlayerScreenState.Loading,
    mediaItem: MediaItem? = null,
    onItemClick: (index: Int) -> Unit = {}
) {

    val mediaItems = state.items
    Scaffold {

        if (state == MainViewModel.PlayerScreenState.Loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = Constants.SIDE_PADDING)
        ) {


            HomeScreenActionBar(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(bottom = 4.dp),

            )

            LazyColumn(
                modifier = modifier.fillMaxSize().padding(horizontal = Constants.SIDE_PADDING),
                state = rememberLazyListState()
            ) {

                itemsIndexed(mediaItems) { index, item ->
                    SongItemUi(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Constants.rectanglesCorner))
                            .clickable { onItemClick(index) }
                            .padding(
                                end = Constants.SIDE_PADDING,
                                start = Constants.SIDE_PADDING
                            ),
                        song = item,
                        showEqualizer = item.mediaId == mediaItem?.mediaId,
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}