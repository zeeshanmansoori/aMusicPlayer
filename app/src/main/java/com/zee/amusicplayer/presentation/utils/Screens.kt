package com.zee.amusicplayer.presentation.utils

import androidx.annotation.DrawableRes
import com.zee.amusicplayer.R


sealed class Screen(
    val position: Int,
    val title: String,
    @DrawableRes val IconId: Int
) {
    data object HomeScreen : Screen(0, "Home",R.drawable.ic_home)
    data object AlbumScreen : Screen(1, "Albums",R.drawable.ic_album)
    data object ArtistsScreen : Screen(2, "Artist",R.drawable.ic_artist)
    data object PlayListScreen : Screen(3, "Playlists",R.drawable.ic_playlists)

    companion object {
        val asList = listOf(
            HomeScreen,
            AlbumScreen,
            ArtistsScreen,
            PlayListScreen
        )

        val size get() = asList.size

    }
}