@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)

package com.zee.amusicplayer.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.zee.amusicplayer.presentation.homeTab.HomeScreen
import com.zee.amusicplayer.presentation.main.components.BottomBar
import com.zee.amusicplayer.presentation.main.components.PlayerBottomSheetUi
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {

    val navController = rememberNavController()
    val readPermissionState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(android.Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    if (readPermissionState.status.isGranted)
        PermissionGrantedUI(navController, viewModel)
    else PermissionDeniedUI(readPermissionState)


}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionDeniedUI(readPermissionState: PermissionState) {
    PermissionNotGranted {
        readPermissionState.launchPermissionRequest()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
private fun PermissionGrantedUI(navController: NavHostController, viewModel: MainViewModel) {

    val bottomSheetState = rememberBottomSheetScaffoldState()

    val bottomBarHeightInPx = with(LocalDensity.current) { Constants.bottomBarHeight.toPx() }

    val scope = rememberCoroutineScope()


    Scaffold(
        Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                bottomBarHeightInPx,
                bottomSheetState,
                navController
            )
        },
    )
    {

        BottomSheetScaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
//                sheetPeekHeight = toolBarHeight + Constants.bottomBarHeight + 4.dp,
            sheetContent = {
                PlayerBottomSheetUi(
                    bottomSheetState,
                    viewModel
                )
            },
            scaffoldState = bottomSheetState
        ) {

            NavHost(
                navController = navController,
                startDestination = Screen.HomeScreen.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = Constants.bottomSheetBottomMargin)
            ) {

                composable(Screen.HomeScreen.route) {
                    val homeState = viewModel.playerScreenState.collectAsState()
                    val playerState = viewModel.playerState.collectAsState()
                    HomeScreen(
                        state = homeState.value,
                        mediaItem = playerState.value.item,
                        onItemClick = { position ->
                            val bottomSheetCollapsed = bottomSheetState.bottomSheetState.isCollapsed
                            if (bottomSheetCollapsed) scope.launch {
                                bottomSheetState.bottomSheetState.expand()
                            }
                            viewModel.onItemClick(position)

                        },
                    )
                }


                composable(Screen.AlbumScreen.route) {
//                        val parentEntry = remember(backStackEntry) {
//                            navController.getBackStackEntry(Screen.HomeScreen.route)
//                        }
//                        AlbumScreen(hiltViewModel(parentEntry))
                }

                composable(Screen.ArtistsScreen.route) { backStackEntry ->
//                        val parentEntry = remember(backStackEntry) {
//                            navController.getBackStackEntry(Screen.HomeScreen.route)
//                        }
//                        ArtistScreen(hiltViewModel<ArtistVieModel>(parentEntry))
                }

                composable(Screen.PlayListScreen.route) {
//                        PlayListScreen()
                }
            }
        }

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