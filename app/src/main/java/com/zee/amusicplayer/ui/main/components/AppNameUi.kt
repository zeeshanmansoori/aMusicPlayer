package com.zee.amusicplayer.ui.main.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


@Composable
fun AppNameUi(modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        append("a ")
        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
            append("Music")
        }

        return@buildAnnotatedString
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
        letterSpacing = 1.sp
    )

}



