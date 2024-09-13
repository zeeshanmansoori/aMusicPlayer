package com.zee.amusicplayer.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SearchScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TextField(value = "zeeshan", onValueChange = {})
        Spacer(modifier = Modifier.height(10.dp) )
        LazyColumn {
            items(10) {

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), text = "item $it")
            }
        }
    }
}


@Preview
@Composable
fun SearchScreenPreview() {

}