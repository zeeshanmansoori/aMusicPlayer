package com.zee.amusicplayer.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenActionBar
import com.zee.amusicplayer.presentation.songs_screen.SongsScreen
import com.zee.amusicplayer.presentation.songs_screen.SongsVieModel
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Constants.paddingStart


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(songViewModel: SongsVieModel, bottomSheetState: BottomSheetScaffoldState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingStart)
            .padding(vertical = Constants.toolBarHeight),
//        contentPadding = PaddingValues(top = )
    ) {
        HomeScreenActionBar(modifier = Modifier.padding(4.dp).padding(bottom =4.dp))
        SongsScreen(viewModel = songViewModel, bottomSheetState = bottomSheetState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AMusicPlayerTheme {
        HomeScreen(
            songViewModel = viewModel(),
            bottomSheetState = rememberBottomSheetScaffoldState()
        )
    }
}
