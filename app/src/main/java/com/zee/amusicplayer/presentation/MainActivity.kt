package com.zee.amusicplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zee.amusicplayer.presentation.albums_screen.AlbumScreen
import com.zee.amusicplayer.presentation.artists_screen.ArtistScreen
import com.zee.amusicplayer.presentation.home_screen.HomeScreen
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenTopBar
import com.zee.amusicplayer.presentation.play_list_screen.PlayListScreen
import com.zee.amusicplayer.presentation.songs_screen.SongsScreen
import com.zee.amusicplayer.presentation.z_components.CustomBottomNavigation
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme
import com.zee.amusicplayer.utils.Constants.toolBarHeight
import com.zee.amusicplayer.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMusicPlayerTheme {

                val navController = rememberAnimatedNavController()

                val viewModel: MainVieModel = hiltViewModel()


                val screen = viewModel.currentScreen.value


                val toolbarHeightPx = with(LocalDensity.current) { toolBarHeight.toPx() }


                val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

                val nestedScrollConnection = remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {

                            val delta = available.y
                            val newOffset = toolbarOffsetHeightPx.value + delta
                            toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)

                            return Offset.Zero
                        }


                    }
                }
                Scaffold(
                    bottomBar = {
                        CustomBottomNavigation(
                            modifier = Modifier,
                            currentScreenId = screen.route,
                            onItemSelected = { route ->
                                if (screen.route != route) {
                                    navController.navigate(route)
                                    viewModel.updateScreen(Screen.getScreenFromRoute(route))
                                    toolbarOffsetHeightPx.value = 0f
                                }

                            }
                        )
                    }) {


                    Box(
                        Modifier
                            .fillMaxSize()
                            .nestedScroll(nestedScrollConnection)
                    ) {


                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route,
                            enterTransition = {
                                fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = .85f)
                            },
                            exitTransition = {
                                fadeOut(animationSpec = tween(200)) + scaleOut(
                                    targetScale = .85f
                                )
                            },

                            ) {

                            composable(Screen.HomeScreen.route) {
                                HomeScreen()
                            }

                            composable(Screen.SongsScreen.route) {
                                SongsScreen()
                            }

                            composable(Screen.AlbumScreen.route) {
                                AlbumScreen()
                            }

                            composable(Screen.ArtistsScreen.route) {
                                ArtistScreen()
                            }

                            composable(Screen.PlayListScreen.route) {
                                PlayListScreen()
                            }
                        }

                        HomeScreenTopBar(
                            screen = screen,
                            modifier = Modifier,
                            offset = toolbarOffsetHeightPx.value
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AMusicPlayerTheme {
        HomeScreen()
    }
}