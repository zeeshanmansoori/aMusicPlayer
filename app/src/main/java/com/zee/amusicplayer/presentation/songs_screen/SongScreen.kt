package com.zee.amusicplayer.presentation.songs_screen

import android.support.v4.media.MediaMetadataCompat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Resource
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun SongsScreen(
    viewModel: SongsVieModel,
    bottomSheetState: BottomSheetScaffoldState,
    nestedScrollConnection: NestedScrollConnection,
) {
    val state = viewModel.mediaItems.value
    val currentlyPlaying = viewModel.curPlayingSong.value
    val bottomSheetCollapsed = bottomSheetState.bottomSheetState.isCollapsed
    val scope = rememberCoroutineScope()

    when (state) {
        is Resource.Success -> {
            SongContent(
                songs = state.data ?: emptyList(),
                currentSongItem = currentlyPlaying,
                nestedScrollConnection = nestedScrollConnection
            ) {

                viewModel.playOrToggleSong(it)
                if (bottomSheetCollapsed)
                    scope.launch {
                        bottomSheetState.bottomSheetState.expand()
//                        bottomSheetState.bottomSheetState.animateTo(
//                            BottomSheetValue.Expanded,
//                            anim = tween(300)
//                        )
                    }

            }
        }

        else -> {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                if (state is Resource.Loading)
                    CircularProgressIndicator()
                else Text(text = "${state.message}", style = MaterialTheme.typography.h2)
            }
        }

    }


}


@Composable
fun SongContent(
    songs: List<SongItem>,
    currentSongItem: MediaMetadataCompat?,
    nestedScrollConnection: NestedScrollConnection,
    togglePlay: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
//        contentPadding = PaddingValues(top = Constants.toolBarHeight),
        state = rememberLazyListState()
    ) {

        itemsIndexed(songs, key = { index, item ->
            item.id//Helps to cache the UI Item for better scrolling
        }) { index, song ->
            SingleSongItem(
                modifier = Modifier
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))
                    .clickable {
                        togglePlay(song.id.toString())
                    }
                    .padding(
                        end = Constants.paddingStart,
                        start = Constants.paddingStart
                    ), song = song,
                showEqualizer = song.id.toString() == currentSongItem?.description?.mediaId.toString()
            )
        }


    }
}

