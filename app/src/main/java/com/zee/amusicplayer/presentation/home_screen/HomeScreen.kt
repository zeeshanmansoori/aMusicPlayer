package com.zee.amusicplayer.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenActionBar
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenTopBar
import com.zee.amusicplayer.presentation.songs_screen.SongsScreen
import com.zee.amusicplayer.presentation.songs_screen.SongsViewModel
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme
import com.zee.amusicplayer.utils.Constants.bottomBarHeight
import com.zee.amusicplayer.utils.Constants.paddingStart

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(songViewModel: SongsViewModel, bottomSheetState: BottomSheetScaffoldState) {

    val toolbarHeightPx = with(LocalDensity.current) { bottomBarHeight.toPx() }

    val toolbarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset, source: NestedScrollSource
            ): Offset {

                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }


        }
    }

    Scaffold(
        topBar = {
            HomeScreenTopBar()
        },
//        backgroundColor = Color.Blue,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(paddingStart)
        ) {

            HomeScreenActionBar(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(bottom = 4.dp)
                    .offset { IntOffset(0, toolbarOffsetHeightPx.floatValue.toInt()) }
            )
            SongsScreen(
                viewModel = songViewModel,
                bottomSheetState = bottomSheetState,
                modifier = Modifier
                    .fillMaxSize()
//                    .nestedScroll(nestedScrollConnection)
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AMusicPlayerTheme {
        HomeScreen(
            songViewModel = viewModel(), bottomSheetState = rememberBottomSheetScaffoldState()
        )
    }
}
