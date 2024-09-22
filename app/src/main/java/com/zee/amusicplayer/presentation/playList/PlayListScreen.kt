package com.zee.amusicplayer.presentation.playList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.domain.model.PlayList
import com.zee.amusicplayer.domain.useCase.playlist.PlayListUseCase
import com.zee.amusicplayer.presentation.playList.component.PlayListItemUi
import com.zee.amusicplayer.utils.Constants


@Composable
fun PlayListScreen(playListUseCase: PlayListUseCase) {

    val list = playListUseCase.playList.collectAsState().value

    Box(Modifier.fillMaxSize()) {


        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = Constants.SIDE_PADDING)
        ) {

            itemsIndexed(list) { index: Int, playList: PlayList ->

                PlayListItemUi(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Constants.rectanglesCorner))
                        .clickable {
//                        showToast(
//                            context = context,
//                            "this feature is not available yet."
//                        )
                        }
                        .padding(
                            start = if (index % 2 == 0) 0.dp else 4.dp,
                            end = if (index % 2 == 0) 4.dp else 0.dp
                        ),
                    playList = playList
                )
            }

        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            onClick = {
                playListUseCase.addNewPlayList("dummy")
            }) {
            Icon(Icons.Filled.Add, null)
        }
    }
}