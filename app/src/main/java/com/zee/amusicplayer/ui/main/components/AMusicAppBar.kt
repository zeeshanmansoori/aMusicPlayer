package com.zee.amusicplayer.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zee.amusicplayer.utils.Constants
import kotlin.math.roundToInt

@Composable
fun AMusicAppBar(
    modifier: Modifier = Modifier.height(Constants.toolBarHeight),
    offset: Float = 0f,
    content: @Composable BoxScope.() -> Unit = {
        AppNameUi(Modifier.align(Alignment.Center))
    },
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(0, offset.roundToInt()) }
            .background(color = MaterialTheme.colors.surface),
        contentAlignment = Alignment.CenterEnd,
        content = content
    )
}