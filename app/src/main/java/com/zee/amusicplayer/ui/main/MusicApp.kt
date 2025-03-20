package com.zee.amusicplayer.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zee.amusicplayer.ui.album.AlbumScreen
import com.zee.amusicplayer.ui.artists.ArtistScreen
import com.zee.amusicplayer.ui.home.HomeScreen
import com.zee.amusicplayer.ui.pbSheet.component.PlayerBottomSheetContent
import com.zee.amusicplayer.ui.pbSheet.component.PlayerCollapseBar
import com.zee.amusicplayer.ui.playList.PlayListScreen
import com.zee.amusicplayer.utils.Screen
import com.zee.amusicplayer.utils.currentFraction
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun MusicApp(viewModel: MainViewModel) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val peekHeight = 50.dp
    val btmNavBarHeight = 80.dp

    val fraction = scaffoldState.currentFraction(peekHeight,btmNavBarHeight)



    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            BottomSheetScaffold(
                sheetContent = {
                    PlayerBottomSheetContent()
                },
                sheetShape = RoundedCornerShape(0.dp),
                sheetPeekHeight = peekHeight,
                sheetDragHandle = {
                    PlayerCollapseBar(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface)
                            .alpha(fraction),
                        playerState = MainViewModel.PlayerState.NotPlaying,
                        onPlayPauseClick = viewModel::onPlayPauseClick,
                        onHeaderClicked = {

                            if (scaffoldState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }

                            }

                        })
                },
                scaffoldState = scaffoldState,
                sheetShadowElevation =0.dp

            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.title,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Screen.HomeScreen.title) { HomeScreen(viewModel) }
                    composable(route = Screen.AlbumScreen.title) { AlbumScreen(viewModel.albumUseCase) }
                    composable(route = Screen.ArtistsScreen.title) { ArtistScreen(viewModel.artistsUseCase) }
                    composable(route = Screen.PlayListScreen.title) { PlayListScreen(viewModel.playListUseCase) }

                }
            }
        }



        BottomAppBar(modifier = Modifier
            .offset {
                IntOffset(
                    0, (btmNavBarHeight.toPx() *(1 - fraction)).roundToInt()
                )
            }
            .height(btmNavBarHeight)) {
            val navBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentDestination = navBackStackEntry?.destination
            Screen.asList.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.iconId),
                            contentDescription = screen.title
                        )
                    },
                    label = { Text(screen.title) },
                    selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(
                            screen.title, null
                        )
                    } == true,
                    onClick = {
                        navController.navigate(screen.title) {

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            launchSingleTop = true
                            // Restore state when reelecting a previously selected item
                            restoreState = true
                        }
                    },
                )
            }
        }


    }

}

@Preview
@Composable
fun MainScreenPreview() {
    MusicApp(viewModel = viewModel())
}