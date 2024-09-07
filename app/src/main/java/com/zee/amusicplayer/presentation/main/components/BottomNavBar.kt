package com.zee.amusicplayer.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zee.amusicplayer.presentation.utils.Screen
import com.zee.amusicplayer.presentation.utils.currentFraction
import com.zee.amusicplayer.utils.Constants
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    bottomBarHeight: Dp,
    bottomSheetState: BottomSheetScaffoldState,
    pagerState: PagerState
) {

    val scope = rememberCoroutineScope()
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Screen.asList.forEach { screen ->
                BottomNavBarItem(
                    modifier = Modifier.weight(1f),
                    screen = screen,
                    isSelected = screen.position == pagerState.currentPage,
                    onItemSelected = { position ->
                        if (pagerState.currentPage != position) {
                            scope.launch {
                                pagerState.scrollToPage(position)

                            }
                        }

                    }
                )
            }
        }

    }

}
