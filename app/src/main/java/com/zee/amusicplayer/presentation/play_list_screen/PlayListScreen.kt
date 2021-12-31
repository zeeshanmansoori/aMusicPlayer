package com.zee.amusicplayer.presentation.play_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zee.amusicplayer.utils.Constants


@Composable
fun PlayListScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = Constants.paddingStart),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "You have no playLists", style = MaterialTheme.typography.h6)
    }
}


@Preview(showBackground = true)
@Composable
fun PlayListScreenPreview() {

}