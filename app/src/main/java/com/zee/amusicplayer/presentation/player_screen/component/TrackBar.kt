package com.zee.amusicplayer.presentation.player_screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.ui.theme.TextColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToLong


@Composable
fun TrackBar(
    modifier: Modifier = Modifier,
    progress: Long,
    max: Long,
    seekTo: (Long) -> Unit
) {

    var interactionStarted by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()


    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Text(text = formattedTime(progress), fontSize = 14.sp, color = TextColor)
        var secondaryProgress by remember {
            mutableStateOf(0f)
        }


        Slider(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1f),
            value = if (!interactionStarted) progress.toFloat() else secondaryProgress,
            valueRange = 0f..max.toFloat(),
            onValueChange = {
                interactionStarted = true
                secondaryProgress = it
            },
            onValueChangeFinished = {

                scope.launch {
                    if (progress.toFloat() != secondaryProgress) {
                        seekTo(secondaryProgress.roundToLong())
                    }

                    delay(100)
                    interactionStarted = false
                }

            }
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