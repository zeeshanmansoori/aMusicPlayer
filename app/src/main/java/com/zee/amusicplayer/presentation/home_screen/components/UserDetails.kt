package com.zee.amusicplayer.presentation.home_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.R

@Composable
fun UserDetails(
    modifier: Modifier = Modifier,
    userName: String = "User Name",
    paddingStart: Dp = 3.dp
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier
                .width(40.dp)
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )

        val annotatedString = buildAnnotatedString {

            withStyle(SpanStyle(fontWeight = FontWeight.W400, fontSize = 14.sp)) {
                append("Welcome,\n")
            }
            withStyle(SpanStyle(fontWeight = FontWeight.W700, fontSize = 19.sp)) {
                append(userName)
            }
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = paddingStart ), text = annotatedString
        )

    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsPrev() {
    UserDetails()
}
