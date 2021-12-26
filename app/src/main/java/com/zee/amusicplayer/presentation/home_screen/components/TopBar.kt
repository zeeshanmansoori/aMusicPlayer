package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.ui.theme.IconTintColor
import kotlin.math.roundToInt

@Composable
fun HomeScreenTopBar(
    modifier: Modifier = Modifier,
    offset: Float = 0f,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(0, offset.roundToInt()) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_serch),
                contentDescription = "search",
                tint = IconTintColor
            )

        }


        val annotatedString = buildAnnotatedString {

            append("Retro ")
            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                append("Music")
            }

        }

        Text(
            text = annotatedString,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            letterSpacing = 1.sp
        )

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "settings", tint = IconTintColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPrev() {

}