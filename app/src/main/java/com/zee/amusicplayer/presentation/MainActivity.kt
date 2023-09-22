package com.zee.amusicplayer.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.zee.amusicplayer.presentation.home_screen.HomeScreen
import com.zee.amusicplayer.presentation.home_screen.components.PermissionNotGranted
import com.zee.amusicplayer.presentation.songs_screen.HomeViewModel
import com.zee.amusicplayer.presentation.z_components.MainActivityComponent
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme
import com.zee.amusicplayer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onStart() {
        super.onStart()


    }

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

        val homeViewModel: HomeViewModel = hiltViewModel()

        val bottomSheetState = rememberBottomSheetScaffoldState()

        val bottomBarHeightInPx = with(LocalDensity.current) { Constants.bottomBarHeight.toPx() }


        Scaffold(
            Modifier.fillMaxSize(),
            bottomBar = {
                MainActivityComponent.BottomBar(
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
                    MainActivityComponent.BottomSheetContent(bottomSheetState, homeViewModel)
                },
                scaffoldState = bottomSheetState
            ) {
                MainActivityComponent.MainActivityContent(
                    homeViewModel,
                    bottomSheetState,
                    navController,
                    modifier = Modifier.fillMaxSize().padding(bottom = Constants.bottomSheetBottomMargin)
                )
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
            homeViewModel = viewModel(), bottomSheetState = rememberBottomSheetScaffoldState()
        )
    }
}