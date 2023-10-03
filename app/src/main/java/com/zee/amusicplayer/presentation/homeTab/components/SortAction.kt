package com.zee.amusicplayer.presentation.homeTab.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.zee.amusicplayer.R
import com.zee.amusicplayer.data.models.SortByE
import com.zee.amusicplayer.presentation.theme.Blue500
import com.zee.amusicplayer.presentation.theme.Green500
import com.zee.amusicplayer.presentation.theme.Orange500
import com.zee.amusicplayer.presentation.theme.Purple500
import com.zee.amusicplayer.presentation.theme.Red500

data class SortAction(
    val color: Color,
    val title: String,
    @DrawableRes val icon: Int,
    val sortBy: SortByE,
) {
    companion object {
        fun getItems() = listOf(
            SortAction(Orange500, "Name", R.drawable.ic_trending, SortByE.Name),
            SortAction(Blue500, "History", R.drawable.ic_history, SortByE.History),
            SortAction(Red500, "Last added", R.drawable.ic_recently_added, SortByE.LastAdded),
            SortAction(Purple500, "Most played", R.drawable.ic_trending, SortByE.MostPlayed),
            SortAction(Green500, "Shuffle", R.drawable.ic_shuffle, SortByE.Shuffle),
        )
    }
}
