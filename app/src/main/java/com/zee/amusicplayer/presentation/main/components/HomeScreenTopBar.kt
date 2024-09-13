package com.zee.amusicplayer.presentation.main.components

import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import com.zee.amusicplayer.R
import com.zee.amusicplayer.presentation.theme.IconTintColor
import com.zee.amusicplayer.utils.AppScreen
import com.zee.amusicplayer.utils.Constants
import kotlin.math.roundToInt

@Composable
fun HomeScreenTopBar(
    controller: NavController,
    modifier: Modifier = Modifier,
    offset: Float = 0f,
    route: String? = null
) {

//    val screen = Screen.getScreenFromRoute(route) ?: return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Constants.toolBarHeight)
            .offset { IntOffset(0, offset.roundToInt()) }
            .background(color = MaterialTheme.colors.surface)
            .padding(vertical = 2.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            controller.navigate(AppScreen.SearchScreen.name)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_serch),
                contentDescription = "search",
                tint = IconTintColor
            )

        }


        val annotatedString = buildAnnotatedString {
                append("a ")
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                    append("Music")
                }

                return@buildAnnotatedString
        }


        Text(
            text = annotatedString,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            letterSpacing = 1.sp
        )

        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = "Settings",
                tint = IconTintColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPrev() {

}