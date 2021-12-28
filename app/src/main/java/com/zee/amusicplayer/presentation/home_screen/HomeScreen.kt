package com.zee.amusicplayer.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.presentation.home_screen.components.HomeScreenActionBar
import com.zee.amusicplayer.presentation.home_screen.components.UserDetails
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Constants.paddingStart


@Composable
fun HomeScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingStart),
        contentPadding = PaddingValues(top = Constants.toolBarHeight)
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


    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}