package com.zee.amusicplayer.presentation.player_screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.ui.theme.TextColor

@ExperimentalMaterialApi
@Composable
fun TrackBar(modifier: Modifier = Modifier) {

    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Text(text = "00:14", fontSize = 14.sp, color = TextColor)
        Slider(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1f),
            value = 10f,
            valueRange = 0f..100f,
            onValueChange = {})
        Text(text = "21:14", fontSize = 14.sp, color = TextColor)
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun TrackBarPreview() {
    TrackBar()
}