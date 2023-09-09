package com.zee.amusicplayer.presentation.player_screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomControllerBar(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Row(
        modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable(role = null) {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
    }
}

//@Preview
//@Composable
//fun BottomControllerBarPreview() {
//    BottomControllerBar()
//}

