package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Resource
import com.zee.amusicplayer.utils.log


@Composable
fun SongsScreen(
    viewModel: SongsVieModel
) {
    val state = viewModel.mediaItems.value
    val context = LocalContext.current


    LaunchedEffect(key1 = true){
        log("songviewmodel SongsScreen $viewModel")
    }
    when (state) {
        is Resource.Success -> {
            SongContent(songs = state.data ?: emptyList(), viewModel)
        }
        else -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(top = Constants.toolBarHeight),
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
fun SongContent(songs: List<SongItem>, viewModel: SongsVieModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(top = Constants.toolBarHeight),
        state = rememberLazyListState()
    ) {

        items(songs) { song ->
            SingleSongItem(modifier = Modifier
                .clip(RoundedCornerShape(Constants.rectanglesCorner))
                .clickable {
                    //context.startActivity(Intent(context, NewActivity::class.java))
                    viewModel.playOrToggleSong(song)
                }
                .padding(
                    end = Constants.paddingStart.plus(4.dp),
                    start = Constants.paddingStart
                ), song = song
            )
        }


    }
}

//@Preview(showBackground = true)
//@Composable
//fun SongsScreenPreview() {
//    SongsScreen()
//}