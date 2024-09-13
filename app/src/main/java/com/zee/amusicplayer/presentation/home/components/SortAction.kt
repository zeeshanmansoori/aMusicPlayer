package com.zee.amusicplayer.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.zee.amusicplayer.R
import com.zee.amusicplayer.utils.SortBy
import com.zee.amusicplayer.presentation.theme.Blue500
import com.zee.amusicplayer.presentation.theme.Green500
import com.zee.amusicplayer.presentation.theme.Orange500
import com.zee.amusicplayer.presentation.theme.Purple500
import com.zee.amusicplayer.presentation.theme.Red500

data class SortAction(
    val color: Color,
    val title: String,
    @DrawableRes val icon: Int,
    val sortBy: SortBy,
) {
    companion object {
        fun getItems() = listOf(
            SortAction(Orange500, "Name", R.drawable.ic_trending, SortBy.Name),
            SortAction(Blue500, "History", R.drawable.ic_history, SortBy.History),
            SortAction(Red500, "Recently added", R.drawable.ic_recently_added, SortBy.LastAdded),
            SortAction(Purple500, "Most played", R.drawable.ic_trending, SortBy.MostPlayed),
            SortAction(Green500, "Shuffle", R.drawable.ic_shuffle, SortBy.Shuffle),
        )
    }
}
