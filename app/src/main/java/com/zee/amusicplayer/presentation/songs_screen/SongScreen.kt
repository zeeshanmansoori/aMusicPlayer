package com.zee.amusicplayer.presentation.songs_screen

import android.support.v4.media.MediaMetadataCompat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Resource
import com.zee.amusicplayer.utils.log
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsScreen(
    viewModel: SongsVieModel,
    bottomSheetState: BottomSheetScaffoldState
) {
    val state = viewModel.mediaItems.value
    val currentlyPlaying = viewModel.curPlayingSong.value
    val bottomSheetCollapsed = !bottomSheetState.bottomSheetState.isVisible
    val scope = rememberCoroutineScope()

    when (state) {
        is Resource.Success -> {
            SongContent(songs = state.data ?: emptyList(), currentSongItem = currentlyPlaying) {

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
                else Text(text = "${state.message}", style = MaterialTheme.typography.headlineMedium)
            }
        }

    }


}


@Composable
fun SongContent(
    songs: List<SongItem>,
    currentSongItem: MediaMetadataCompat?,
    togglePlay: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
//        contentPadding = PaddingValues(top = Constants.toolBarHeight),
        state = rememberLazyListState()
    ) {

        items(songs) { song ->
            SingleSongItem(
                modifier = Modifier
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))
                    .clickable {
                        log("song $song")
                        togglePlay(song.id.toString())
                    }
                    .padding(
                        end = Constants.paddingStart.plus(4.dp),
                        start = Constants.paddingStart
                    ), song = song,
                showEqualizer = song.id.toString() == currentSongItem?.description?.mediaId.toString()
            )
        }


    }
}

