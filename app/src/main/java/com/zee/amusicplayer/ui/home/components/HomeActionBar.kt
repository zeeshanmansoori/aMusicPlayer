package com.zee.amusicplayer.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.utils.SortBy

@Composable
fun HomeActionBar(
    selectedSortBy: SortBy,
    onSortActionChange: (item: SortBy) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier
            .background(MaterialTheme.colors.surface)
            .padding(bottom = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val items = SortAction.getItems()

        itemsIndexed(items) { index, item ->
            val padding = 10.dp
            HomeActionBarChip(
                modifier = Modifier.padding(
                    start = if (index == 0) padding else 0.dp,
                    end = padding,
                    top = 5.dp,
                    bottom = 5.dp,
                ),
                item = item,
                selected = item.sortBy == selectedSortBy,
                onSortActionChange = onSortActionChange
            )
        }

    }
}

@Composable
fun HomeActionBarChip(
    modifier: Modifier = Modifier,
    item: SortAction = SortAction.getItems().first(),
    selected: Boolean = false,
    onSortActionChange: (item: SortBy) -> Unit = {},
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(if (selected) MaterialTheme.colors.background else MaterialTheme.colors.surface)
            .clickable {
                onSortActionChange.invoke(item.sortBy)
            }
            .padding(10.dp)
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            tint = item.color.copy(alpha = if (selected) 1f else 0.4f),
            modifier = Modifier
                .size(20.dp)
        )
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = item.title,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 14.sp,
                fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }

}