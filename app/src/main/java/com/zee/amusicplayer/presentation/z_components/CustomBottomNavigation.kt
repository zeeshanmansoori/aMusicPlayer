package com.zee.amusicplayer.presentation.z_components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import com.zee.amusicplayer.ui.theme.TextColor
import com.zee.amusicplayer.utils.Screen


@Preview
@Composable
fun CustomBottomNavPreview() {
    CustomBottomNavigation(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .padding(vertical = 4.dp),
        currentScreenId = Screen.HomeScreen.route,
        onItemSelected = {}
    )
}

@Composable
fun CustomBottomNavigation(
    modifier: Modifier,
    currentScreenId: String,
    onItemSelected: (String) -> Unit
) {


    // in order to clip the ripple effect vertically i putted content inside surface
    // other than surface nothing working
    Surface {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            Screen.toList().forEach { screen ->
                SingleBottomNavigationItem(
                    modifier = Modifier.weight(1f),
                    screen = screen,
                    isSelected = currentScreenId == screen.route,
                    onItemSelected = onItemSelected
                )
            }
        }
    }

}

@Composable
fun SingleBottomNavigationItem(
    modifier: Modifier,
    screen: Screen,
    isSelected: Boolean,
    onItemSelected: (String) -> Unit

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
        )

        val textColor: Color by animateColorAsState(
            if (isSelected) MaterialTheme.colors.primary else TextColor
        )

        val iconColor: Color by animateColorAsState(
            if (isSelected) MaterialTheme.colors.primary else TextColor
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = boxBgColor)
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = screen.IconId),
                contentDescription = screen.title,
                Modifier.size(20.dp),
                tint = iconColor

            )
        }

        Text(
            text = screen.title,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SingleBottomNavigationItemPreview1() {
    SingleBottomNavigationItem(
        Modifier,
        screen = Screen.HomeScreen,
        isSelected = true,
        onItemSelected = {})
}



