package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.Screen
import kotlin.math.roundToInt

@Composable
fun HomeScreenTopBar(
    modifier: Modifier = Modifier,
    offset: Float = 0f,
    route: String?
) {

    val screen = Screen.getScreenFromRoute(route) ?: return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Constants.toolBarHeight)
            .offset { IntOffset(0, offset.roundToInt()) }
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 2.dp, horizontal = 2.dp),
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
            if (screen == Screen.HomeScreen) {
                append("a ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Music")
                }

                return@buildAnnotatedString
            }

            append(screen.title)

        }


        Text(
            text = annotatedString,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            letterSpacing = 1.sp
        )

        IconButton(onClick = { }) {
            Icon(
                painter = if (screen == Screen.HomeScreen) painterResource(id = R.drawable.ic_settings) else painterResource(
                    id = R.drawable.ic_more_vert
                ),
                contentDescription = if (screen == Screen.HomeScreen) "settings" else "menu",
                tint = IconTintColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPrev() {

}