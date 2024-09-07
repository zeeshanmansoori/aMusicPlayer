package com.zee.amusicplayer.presentation.main.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.presentation.theme.TextColor
import com.zee.amusicplayer.presentation.utils.Screen


@Composable
fun BottomNavBarItem(
    modifier: Modifier,
    screen: Screen,
    isSelected: Boolean,
    onItemSelected: (String) -> Unit,
) {

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 45.dp),
            ) {
                onItemSelected(screen.route)
            }
            .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),

        ) {

        val boxBgColor: Color by animateColorAsState(
            if (isSelected) MaterialTheme.colors.primary.copy(alpha = .1f) else Color.Transparent,
            label = "",
        )

        val textColor: Color by animateColorAsState(
            if (isSelected) MaterialTheme.colors.primary else TextColor, label = ""
        )

        val iconColor: Color by animateColorAsState(
            if (isSelected) MaterialTheme.colors.primary else TextColor, label = ""
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = boxBgColor)
                .fillMaxWidth()
                .padding(vertical = 6.dp), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = screen.IconId),
                contentDescription = screen.title,
                Modifier.size(20.dp),
                tint = iconColor

            )
        }

        Text(
            text = screen.title, color = textColor, fontSize = 14.sp, fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SingleBottomNavigationItemPreview1() {
    BottomNavBarItem(
        Modifier,
        screen = Screen.HomeScreen,
        isSelected = true,
        onItemSelected = {},
    )
}



