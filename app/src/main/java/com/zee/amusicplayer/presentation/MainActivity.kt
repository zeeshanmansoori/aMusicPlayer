package com.zee.amusicplayer.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
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
import com.zee.amusicplayer.presentation.songs_screen.SongsVieModel
import com.zee.amusicplayer.presentation.z_components.MainActivityComponents
import com.zee.amusicplayer.ui.theme.AMusicPlayerTheme
import com.zee.amusicplayer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@OptIn(
    ExperimentalPermissionsApi::class,
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

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnrememberedGetBackStackEntry")
    @Composable
    private fun PermissionGrantedUI(navController: NavHostController) {

        val songViewModel: SongsVieModel = hiltViewModel()

        val bottomSheetState = rememberBottomSheetScaffoldState()

        val bottomBarHeightInPx = with(LocalDensity.current) { Constants.bottomBarHeight.toPx() }


//        val nestedScrollConnection = remember {
//            object : NestedScrollConnection {
//                override fun onPreScroll(
//                    available: Offset, source: NestedScrollSource
//                ): Offset {
//
//                    val delta = available.y
////                            val newOffset = toolbarOffsetHeightPx.value + delta
////                            toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
//                    return Offset.Zero
//                }
//
//
//            }
//        }

        Scaffold(
            Modifier.fillMaxSize(),
            bottomBar = {
                MainActivityComponents.BottomAppBar(bottomBarHeightInPx,bottomSheetState,navController)
            },
        ){
            MainActivityComponents.MainAppContent(it,bottomSheetState,songViewModel,navController)
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AMusicPlayerTheme {
        HomeScreen(
            songViewModel = viewModel(), bottomSheetState = rememberBottomSheetScaffoldState()
        )
    }
}