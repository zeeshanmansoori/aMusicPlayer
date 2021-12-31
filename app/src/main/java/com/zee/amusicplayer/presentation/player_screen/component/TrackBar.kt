package com.zee.amusicplayer.presentation.player_screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.ui.theme.TextColor
import com.zee.amusicplayer.utils.log

@ExperimentalMaterialApi
@Composable
fun TrackBar(
    modifier: Modifier = Modifier,
    progress: Long,
    max: Long,
    updateProgress: (Float) -> Unit
) {

    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Text(text = formattedTime(progress), fontSize = 14.sp, color = TextColor)



        Slider(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1f),
            value = progress.toFloat(),
            valueRange = progress.toFloat()..max.toFloat(),
            onValueChange = updateProgress,
            onValueChangeFinished = {}
        )
        Text(text = formattedTime(max), fontSize = 14.sp, color = TextColor)
    }
}

fun formattedTime(ms: Long): String {
    val mins = (ms / 1000) / 60
    val seconds = (ms / 1000) % 60
    return mins.toTwoDigitWord() + ":" + seconds.toTwoDigitWord()
}

fun Long.toTwoDigitWord() = if (this > 10) "$this" else "0$this"

//@ExperimentalMaterialApi
//@Preview
//@Composable
//fun TrackBarPreview() {
//    TrackBar()
//}