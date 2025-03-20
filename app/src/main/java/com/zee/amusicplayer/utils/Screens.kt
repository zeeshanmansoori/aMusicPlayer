package com.zee.amusicplayer.utils

import androidx.annotation.DrawableRes
import com.zee.amusicplayer.R


sealed class Screen(
    val title: String,
    @DrawableRes val iconId: Int
) {

    object HomeScreen : Screen("Home", R.drawable.ic_home)
    object AlbumScreen : Screen("Albums", R.drawable.ic_album)
    object ArtistsScreen : Screen("Artist", R.drawable.ic_artist)
    object PlayListScreen : Screen("Playlists", R.drawable.ic_playlists)

    companion object {
        val asList by lazy {
            listOf(
                HomeScreen,
                AlbumScreen,
                ArtistsScreen,
                PlayListScreen
            )
        }

        val size get() = asList.size

    }
}