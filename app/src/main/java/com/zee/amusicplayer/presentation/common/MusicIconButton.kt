package com.zee.amusicplayer.presentation.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun MusicIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Filled.SkipPrevious,
    onCLick: () -> Unit,

    ) {

    IconButton(modifier = modifier, onClick = onCLick) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}

@Preview
@Composable
fun MusicIconButtonPreview() {
    MusicIconButton() {

    }
}