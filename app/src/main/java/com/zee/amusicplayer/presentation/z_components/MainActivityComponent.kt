package com.zee.amusicplayer.presentation.z_components

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.zee.amusicplayer.presentation.albums_screen.AlbumScreen
import com.zee.amusicplayer.presentation.artists_screen.ArtistScreen
import com.zee.amusicplayer.presentation.artists_screen.ArtistVieModel
import com.zee.amusicplayer.presentation.home_screen.HomeScreen
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenTopBar
import com.zee.amusicplayer.presentation.home_screen.components.PlayerCollapseBar
import com.zee.amusicplayer.presentation.play_list_screen.PlayListScreen
import com.zee.amusicplayer.presentation.player_screen.PlayerScreen
import com.zee.amusicplayer.presentation.songs_screen.SongsVieModel
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Screen
import com.zee.amusicplayer.utils.currentFraction
import kotlin.math.roundToInt

object MainActivityComponent {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BottomBar(
        bottomBarHeightInPx: Float,
        bottomSheetState: BottomSheetScaffoldState,
        navController: NavHostController
    ) {

        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry.value?.destination?.route

        CustomBottomNavigation(
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface)
                .height(Constants.bottomBarHeight)
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        0, (bottomBarHeightInPx * bottomSheetState.currentFraction).roundToInt()
                    )
                },
            currentRoute = currentRoute,
            onItemSelected = { route ->
                if (currentRoute != route) {
                    navController.navigate(route)
                    //viewModel.updateScreen(Screen.getScreenFromRoute(route))
//                                    toolbarOffsetHeightPx.value = 0f
                }

            },
        )
    }

    @OptIn(
        ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class,
        ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,
        ExperimentalAnimationApi::class
    )
    @Composable
    fun BottomSheetContent(
        bottomSheetState: BottomSheetScaffoldState,
        songViewModel: SongsVieModel
    ) {
        Column(Modifier.fillMaxSize()) {
            PlayerCollapseBar(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.surface)
                    .alpha(1 - bottomSheetState.currentFraction), songViewModel
            )
            PlayerScreen(
                modifier = Modifier,
                songsVieModel = songViewModel,
                visibility = bottomSheetState.currentFraction,
                bottomSheetState = bottomSheetState
            )

        }
    }

    @SuppressLint("UnrememberedGetBackStackEntry")
    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun MainActivityContent(
        songViewModel: SongsVieModel,
        bottomSheetState: BottomSheetScaffoldState,
        navController: NavHostController,
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry.value?.destination?.route

        Box(
            Modifier
                .fillMaxWidth()
//                .nestedScroll(nestedScrollConnection)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.HomeScreen.route,
//                        enterTransition = {
//                            fadeIn(animationSpec = tween(300)) +(scaleIn(initialScale = .85f))
//                        },
//                        exitTransition = {
//                            fadeOut(animationSpec = tween(200)) +(scaleOut(targetScale = .85f))
//                        },

            ) {

                composable(Screen.HomeScreen.route) {
                    HomeScreen(songViewModel, bottomSheetState)
                }

//                                    composable(Screen.SongsScreen.route) {
//                                        SongsScreen(songViewModel, bottomSheetState)
//                                    }

                composable(Screen.AlbumScreen.route) {
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(Screen.HomeScreen.route)
                    }
                    AlbumScreen(hiltViewModel(parentEntry))
                }

                composable(Screen.ArtistsScreen.route) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(Screen.HomeScreen.route)
                    }
                    ArtistScreen(hiltViewModel<ArtistVieModel>(parentEntry))
                }

                composable(Screen.PlayListScreen.route) {
                    PlayListScreen()
                }
            }

            HomeScreenTopBar(
                route = currentRoute,
                modifier = Modifier,
//                                    offset = toolbarOffsetHeightPx.value
            )
        }
    }

}