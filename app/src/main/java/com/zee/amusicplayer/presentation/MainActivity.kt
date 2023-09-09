package com.zee.amusicplayer.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.zee.amusicplayer.presentation.albums_screen.AlbumScreen
import com.zee.amusicplayer.presentation.artists_screen.ArtistScreen
import com.zee.amusicplayer.presentation.artists_screen.ArtistVieModel
import com.zee.amusicplayer.presentation.home_screen.HomeScreen
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenTopBar
import com.zee.amusicplayer.presentation.home_screen.components.PermissionNotGranted
import com.zee.amusicplayer.presentation.home_screen.components.PlayerCollapseBar
import com.zee.amusicplayer.presentation.play_list_screen.PlayListScreen
import com.zee.amusicplayer.presentation.player_screen.PlayerScreen
import com.zee.amusicplayer.presentation.songs_screen.SongsVieModel
import com.zee.amusicplayer.presentation.z_components.CustomBottomNavigation
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Constants.toolBarHeight
import com.zee.amusicplayer.utils.Screen
import com.zee.amusicplayer.utils.currentFraction
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMusicPlayerTheme {

                val navController = rememberNavController()

                //val viewModel: MainVieModel = hiltViewModel()
                //val screen = viewModel.currentScreen.value
//                val toolbarHeightPx = with(LocalDensity.current) { toolBarHeight.toPx() }
//                val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

                val readPermissionState =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        rememberPermissionState(android.Manifest.permission.READ_MEDIA_AUDIO)
                    } else {
                        rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                if (readPermissionState.status.isGranted)
                    PermissionGrantedUI(navController)
                else PermissionDeniedUI(readPermissionState)


            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun PermissionDeniedUI(readPermissionState: PermissionState) {
        PermissionNotGranted {
            readPermissionState.launchPermissionRequest()
        }
    }

    @SuppressLint("UnrememberedGetBackStackEntry")
    @Composable
    private fun PermissionGrantedUI(navController: NavHostController) {

        val songViewModel: SongsVieModel = hiltViewModel()

        val backStackEntry = navController.currentBackStackEntryAsState()
        val bottomSheetState = rememberBottomSheetScaffoldState()
        val currentRoute = backStackEntry.value?.destination?.route

        val bottomBarHeightInPx = with(LocalDensity.current) { Constants.bottomBarHeight.toPx() }


        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset, source: NestedScrollSource
                ): Offset {

                    val delta = available.y
//                            val newOffset = toolbarOffsetHeightPx.value + delta
//                            toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                    return Offset.Zero
                }


            }
        }

        Scaffold(Modifier.fillMaxSize(), bottomBar = {
            CustomBottomNavigation(modifier = Modifier
                .height(Constants.bottomBarHeight)
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        0, (bottomBarHeightInPx * bottomSheetState.currentFraction).roundToInt()
                    )
                }, currentRoute = currentRoute, onItemSelected = { route ->
                if (currentRoute != route) {
                    navController.navigate(route)
                    //viewModel.updateScreen(Screen.getScreenFromRoute(route))
//                                    toolbarOffsetHeightPx.value = 0f
                }

            })
        }) {

            BottomSheetScaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                sheetPeekHeight = toolBarHeight + Constants.bottomBarHeight + 4.dp,
                sheetContent = {

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
                },
                scaffoldState = bottomSheetState
            ) {


                Box(
                    Modifier
                        .fillMaxWidth()
                        .nestedScroll(nestedScrollConnection)
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