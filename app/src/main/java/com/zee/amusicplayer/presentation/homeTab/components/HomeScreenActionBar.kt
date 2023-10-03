package com.zee.amusicplayer.presentation.homeTab.components

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
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zee.amusicplayer.data.models.SortByE
import com.zee.amusicplayer.presentation.main.MainViewModel

@Composable
fun HomeScreenActionBar(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {

    Column {

        TextField(value = "default search", onValueChange = {})
        val items = SortAction.getItems()
        val selectedSortByE = viewModel.sortByE.collectAsState()

        LazyRow(
            modifier.fillMaxWidth(),
        ) {

            itemsIndexed(items) { index, item ->
                val padding = 10.dp
                HomeActionBarChip(
                    modifier = Modifier.padding(
                        start = if (index == 0) padding else 0.dp,
                        end = padding,
                    ),
                    padding = padding,
                    item = item,
                    selected = item.sortBy == selectedSortByE.value,
                    onSortActionChange = viewModel::onSortActionChange
                )
            }

        }
    }
}

@Composable
fun HomeActionBarChip(
    modifier: Modifier = Modifier,
    padding: Dp = 10.dp,
    item: SortAction = SortAction.getItems().first(),
    selected: Boolean = false,
    onSortActionChange: (item: SortByE) -> Unit = {},
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {
                onSortActionChange.invoke(item.sortBy)
            }
            .padding(vertical = padding / 2, horizontal = padding)
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            tint = item.color.copy(alpha = if (selected) 0.6f else 0.4f),
            modifier = Modifier
                .size(30.dp)
                .padding(end = padding),
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 13.sp,
                color = if (selected) Color.White else Color.DarkGray,
            )
        )
    }

}

