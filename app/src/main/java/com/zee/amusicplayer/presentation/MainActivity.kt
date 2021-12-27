package com.zee.amusicplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.zee.amusicplayer.utils.Screen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMusicPlayerTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberAnimatedNavController()
                val viewModel: MainVieModel = hiltViewModel()
                val screen = viewModel.currentScreen.value

                Scaffold(
                    bottomBar = {
                        CustomBottomNavigation(
                            modifier = Modifier,
                            currentScreenId = screen.route,
                            onItemSelected = { route ->
                                if (screen.route != route) {
                                    navController.navigate(route)
                                    viewModel.updateScreen(Screen.getScreenFromRoute(route))
                                }

                            }
                        )
                    }) {


                    val toolbarHeight = 48.dp
                    val paddingStart = 16.dp
                    val toolbarHeightInPixel = with(LocalDensity.current) { toolbarHeight.toPx() }


                    Box(Modifier.fillMaxSize()) {


                        HomeScreenTopBar(
                            screen = screen,
                            modifier = Modifier
                                .height(toolbarHeight)
                                .padding(horizontal = 2.dp, vertical = 1.dp), offset = 0f
                        )

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