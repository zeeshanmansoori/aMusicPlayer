package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import com.zee.amusicplayer.utils.Constants
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun SongsScreen(
    viewModel: SongsViewModel,
    bottomSheetState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.mediaItems.collectAsState().value
    val currentlyPlaying = viewModel.currentMediaItem.collectAsState()
    val bottomSheetCollapsed = bottomSheetState.bottomSheetState.isCollapsed
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    SongContent(
        songs = state,
        currentMediaItemId = currentlyPlaying.value?.mediaId,
        modifier = modifier
    ) {
        viewModel.playOrToggleSong(it)
        if (bottomSheetCollapsed) scope.launch {
            bottomSheetState.bottomSheetState.expand()
        }

    }

}

@Composable
fun SongContent(
    songs: List<MediaItem>,
    currentMediaItemId: String?,
    modifier: Modifier = Modifier,
    togglePlay: (index: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
//        contentPadding = PaddingValues(top = Constants.toolBarHeight),
        state = rememberLazyListState()
    ) {

        itemsIndexed(songs) { index, song ->
            SingleSongItem(
                modifier = Modifier
                    .clip(RoundedCornerShape(Constants.rectanglesCorner))
                    .clickable {
                        togglePlay(index)
                    }
                    .padding(
                        end = Constants.paddingStart, start = Constants.paddingStart
                    ),
                song = song, showEqualizer = song.mediaId == currentMediaItemId,
            )
        }


    }
}

