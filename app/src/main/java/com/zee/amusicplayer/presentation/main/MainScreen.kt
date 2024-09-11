package com.zee.amusicplayer.presentation.main

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.zee.amusicplayer.presentation.album.AlbumScreen
import com.zee.amusicplayer.presentation.album.AlbumViewModel
import com.zee.amusicplayer.presentation.home.HomeScreen
import com.zee.amusicplayer.presentation.main.components.BottomNavBar
import com.zee.amusicplayer.presentation.main.components.HomeScreenTopBar
import com.zee.amusicplayer.presentation.pbSheet.PlayerBottomSheetScreen
import com.zee.amusicplayer.presentation.utils.Screen
import com.zee.amusicplayer.presentation.utils.UiConstants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val permissions = mutableListOf<String>()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(android.Manifest.permission.READ_MEDIA_AUDIO)
    } else {
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    val permissionState = rememberMultiplePermissionsState(permissions = permissions)

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
private fun PermissionGrantedUI( viewModel: MainViewModel) {

    val bottomSheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val bottomMargin = UiConstants.toolBarHeight + UiConstants.bottomBarHeight

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        val pagerState = rememberPagerState {
            Screen.size
        }

        BottomSheetScaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = { HomeScreenTopBar()},
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
                        val homeState = viewModel.playerScreenState.collectAsState()
                        val playerState = viewModel.playerState.collectAsState()

                        HomeScreen(
                            songs = homeState.value,
                            currentSong = playerState.value.item,
                            onItemClick = { itemPosition ->
                                val bottomSheetCollapsed =
                                    bottomSheetState.bottomSheetState.isCollapsed
                                if (bottomSheetCollapsed) scope.launch {
                                    bottomSheetState.bottomSheetState.expand()
                                }
                                viewModel.onItemClick(itemPosition)
                            },
                        )
                    }

                    Screen.AlbumScreen.position -> {
//                        val viewModel by viewModel<AlbumVieModel>(currentCompositionLocalContext)

//                        val controller = remeberna
//                        NavHost(navController = , graph = )
                        val albumViewModel = AlbumViewModel()
                        LaunchedEffect(key1 = viewModel) {
                            viewModel.playerScreenState.collectLatest {
                                albumViewModel.setUpAlbum(songs = it)
                            }
                        }
                        AlbumScreen(albumViewModel)
                    }

                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Under Construction")
                        }
                    }

//                    Screen.AlbumScreen.position -> {
//
//                    }
//
//                    Screen.ArtistsScreen.position -> {
//
//                    }
//
//                    Screen.PlayListScreen.position -> {
//
//                    }

                }
            }

        }

        BottomNavBar(
            bottomBarHeight = UiConstants.bottomBarHeight,
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