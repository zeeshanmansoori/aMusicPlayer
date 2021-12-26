package com.zee.amusicplayer.presentation.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenActionBar
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenTopBar
import com.zee.amusicplayer.presentation.home_screen.components.UserDetails


@Composable
fun HomeScreen() {

    val toolbarHeight = 48.dp
    val paddingStart = 16.dp
    val toolbarHeightInPixel = with(LocalDensity.current) { toolbarHeight.toPx() }
    //val offset =

    Box(Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingStart),
            contentPadding = PaddingValues(top = toolbarHeight)
        ) {
            item {
                UserDetails(
                    modifier = Modifier.padding(vertical = paddingStart),
                    paddingStart = paddingStart
                )
            }

            item {

                HomeScreenActionBar(modifier = Modifier.padding(4.dp))
            }

            item {

            }
        }
        HomeScreenTopBar(
            modifier = Modifier
                .height(toolbarHeight)
                .padding(horizontal = 2.dp, vertical = 1.dp), offset = 0f
        )
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}