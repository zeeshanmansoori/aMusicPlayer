package com.zee.amusicplayer.ui.main

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.zee.amusicplayer.ui.album.AlbumScreen
import com.zee.amusicplayer.ui.artists.ArtistScreen
import com.zee.amusicplayer.ui.home.HomeScreen
import com.zee.amusicplayer.ui.main.components.BottomNavBar
import com.zee.amusicplayer.ui.pbSheet.PlayerBottomSheetScreen
import com.zee.amusicplayer.ui.playList.PlayListScreen
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Screen


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val permissions = mutableListOf<String>()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(android.Manifest.permission.READ_MEDIA_AUDIO)
        permissions.add(android.Manifest.permission.POST_NOTIFICATIONS)
    } else {
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionState = rememberMultiplePermissionsState(permissions = permissions) {
        // permissions are granted, now we can trigger the fetching again.
        viewModel.triggerFetchMusicWorker()

    }

    if (permissionState.allPermissionsGranted)
        PermissionGrantedUI(viewModel)
    else PermissionDeniedUI(permissionState)


}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionDeniedUI(
    readPermissionState: MultiplePermissionsState,
) {
    PermissionNotGranted {
        readPermissionState.launchMultiplePermissionRequest()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PermissionGrantedUI(viewModel: MainViewModel) {

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val bottomMargin = Constants.toolBarHeight + Constants.bottomBarHeight

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        val pagerState = rememberPagerState {
            Screen.size
        }

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
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = bottomMargin)
            ) { position ->
                when (position) {
                    Screen.HomeScreen.position -> {

                        HomeScreen(
                            viewModel,
                            bottomSheetState
                        )
                    }

                    Screen.AlbumScreen.position -> {
                        AlbumScreen(viewModel.albumUseCase)
                    }


                    Screen.ArtistsScreen.position -> {
                        ArtistScreen(viewModel.artistsUseCase)
                    }

                    Screen.PlayListScreen.position -> {
                        PlayListScreen(viewModel.playListUseCase)
                    }

                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Under Construction")
                        }
                    }


                }
            }
        }

        BottomNavBar(
            bottomBarHeight = Constants.bottomBarHeight,
            bottomSheetState = bottomSheetState,
            pagerState = pagerState
        )
    }
}


@Composable
fun PermissionNotGranted(retryBtn: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Need Storage permission in order to play music",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = retryBtn) {
            Text(
                "Grant Permission",
                style = MaterialTheme.typography.body1,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    }

}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(viewModel = viewModel())
}