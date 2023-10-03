package com.zee.amusicplayer.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zee.amusicplayer.presentation.z_components.CustomBottomNavigation
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.currentFraction
import kotlin.math.roundToInt

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
