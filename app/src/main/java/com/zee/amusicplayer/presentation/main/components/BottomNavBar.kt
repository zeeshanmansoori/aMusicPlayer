package com.zee.amusicplayer.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zee.amusicplayer.presentation.utils.Screen
import com.zee.amusicplayer.presentation.utils.currentFraction
import com.zee.amusicplayer.utils.Constants
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    bottomBarHeight: Dp,
    bottomSheetState: BottomSheetScaffoldState,
    navController: NavHostController
) {

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val bottomBarHeightInPx = with(LocalDensity.current) { bottomBarHeight.toPx() }


    Surface(
        modifier = modifier
            .offset {
                IntOffset(
                    0, (bottomBarHeightInPx * bottomSheetState.currentFraction).roundToInt()
                )
            }
            .height(Constants.bottomBarHeight)
            .fillMaxWidth(),
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            Screen.toList().forEach { screen ->
                BottomNavBarItem(
                    modifier = Modifier.weight(1f),
                    screen = screen,
                    isSelected = currentRoute == screen.route,
                    onItemSelected = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route)
                            //viewModel.updateScreen(Screen.getScreenFromRoute(route))
//                                    toolbarOffsetHeightPx.value = 0f
                        }

                    }
                )
            }
        }
    }

}
