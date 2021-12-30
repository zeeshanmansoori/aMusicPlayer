package com.zee.amusicplayer.presentation.player_screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomControllerBar(modifier: Modifier = Modifier) {

    Row(
        modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(imageVector = Icons.Filled.ArrowDownward, contentDescription = null)
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
    }
}

@Preview
@Composable
fun BottomControllerBarPreview() {
    BottomControllerBar()
}

