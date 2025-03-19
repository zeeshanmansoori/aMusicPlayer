package com.zee.amusicplayer.ui.albumDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.ui.common.MusicImage
import com.zee.amusicplayer.ui.main.MainViewModel
import com.zee.amusicplayer.ui.pbSheet.PlayerBottomSheetScreen
import com.zee.amusicplayer.utils.Constants


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumDetailsScreen(viewModel: MainViewModel) {
    val bottomSheetState = rememberBottomSheetScaffoldState()
    val bottomMargin = Constants.toolBarHeight
    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetPeekHeight = bottomMargin,
        sheetContent = {
            PlayerBottomSheetScreen(
                bottomSheetState,
                viewModel
            )
        },
        scaffoldState = bottomSheetState
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                MusicImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(Constants.rectanglesCorner))
                        .background(color = Color.LightGray),
                    artUri = null,
                )
            }

            item {
                Column {
                    Text("Album Item")
                    Text("Total Songs")
                }
            }

            itemsIndexed(List(20) { }) { index, item ->
                Text("index $item")
            }
        }
    }

}
