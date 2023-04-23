package com.zee.amusicplayer.presentation.home_screen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.ui.theme.Blue500
import com.zee.amusicplayer.ui.theme.Green500
import com.zee.amusicplayer.ui.theme.Purple500
import com.zee.amusicplayer.ui.theme.Red500
import com.zee.amusicplayer.utils.showToast


data class HomeScreenActionItem(val color: Color, val title: String, @DrawableRes val icon: Int) {
    companion object {
        fun getItems() = listOf(
            HomeScreenActionItem(Blue500, "History", R.drawable.ic_history),
            HomeScreenActionItem(Red500, "Last added", R.drawable.ic_recently_added),
            HomeScreenActionItem(Purple500, "Most played", R.drawable.ic_trending),
            HomeScreenActionItem(Green500, "Shuffle", R.drawable.ic_shuffle),
        )
    }
}

@Composable
fun HomeScreenActionBar(modifier: Modifier = Modifier) {

    Column {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = "Sort by",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1
        )
        val items = HomeScreenActionItem.getItems()
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            items.forEach { item ->
                SingleHomeScreenActionBarItem(item.title, item.icon, item.color)
            }

        }
    }
}


@Composable
fun SingleHomeScreenActionBarItem(
    title: String = "History",
    @DrawableRes icon: Int = R.drawable.ic_history,
    bgColor: Color = Blue500
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.clickable {
            showToast(
                context = context,
                "this feature is not available yet."
            )
        }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color = bgColor.copy(alpha = .3f)),
            contentAlignment = Alignment.Center

        ) {

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = bgColor.copy(alpha = .6f)
            )
        }
        Text(text = title, style = MaterialTheme.typography.subtitle1.copy(fontSize = 13.sp))
    }
}

@Preview
@Composable
fun HomeScreenActionBarPrev() {
    HomeScreenActionBar()
}