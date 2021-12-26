package com.zee.amusicplayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zee.amusicplayer.presentation.albums_screen.AlbumScreen
import com.zee.amusicplayer.presentation.artists_screen.ArtistScreen
import com.zee.amusicplayer.presentation.home_screen.HomeScreen
import com.zee.amusicplayer.presentation.play_list_screen.PlayListScreen
import com.zee.amusicplayer.presentation.songs_screen.SongsScreen
import com.zee.amusicplayer.presentation.z_components.CustomBottomNavigation
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme
import com.zee.amusicplayer.utils.Screen

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMusicPlayerTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberAnimatedNavController()

                var currentState by remember {
                    mutableStateOf(Screen.HomeScreen.route)
                }

                Scaffold(bottomBar = {
                    CustomBottomNavigation(
                        modifier = Modifier,
                        currentScreenId = currentState,
                        onItemSelected = { route ->
                            if (currentState != route)
                                navController.navigate(route)
                            currentState = route
                        }
                    )
                }) {

                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AMusicPlayerTheme {
        HomeScreen()
    }
}