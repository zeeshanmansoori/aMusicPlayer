package com.zee.amusicplayer.presentation.play_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.zee.amusicplayer.utils.Screen


@Composable
fun PlayListScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "PlayListScreen")
    }
}


@Preview(showBackground = true)
@Composable
fun PlayListScreenPreview() {

}