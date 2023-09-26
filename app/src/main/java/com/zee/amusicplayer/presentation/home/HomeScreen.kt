package com.zee.amusicplayer.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    items: List<MediaItem> = emptyList(),
    currentIndex: Int = 0,
    onItemClick: (MediaItem, Int) -> Unit = { _, _ -> },
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {

        itemsIndexed(items) { index, item ->

            val isSameItem = index == currentIndex
            Text(text = item.mediaMetadata.title.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 20.dp)
                    .clickable {
                        onItemClick.invoke(item, index)
                    }
                    .padding(10.dp)
                    .background(
                        color = if (isSameItem) Color.Red.copy(alpha = 0.7f) else Color.Gray.copy(
                            alpha = .5f
                        )
                    ),
                fontSize = 18.sp

            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

    HomeScreen(Modifier, List(10) { MediaItem.EMPTY })
}