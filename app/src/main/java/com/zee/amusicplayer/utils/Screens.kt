package com.zee.amusicplayer.utils

import androidx.annotation.DrawableRes
import com.zee.amusicplayer.R


sealed class Screen(val title: String, val route: String, @DrawableRes val IconId: Int) {
    object HomeScreen : Screen("Home", "Home", R.drawable.ic_home)
//    object SongsScreen : Screen("Songs", "Songs", R.drawable.ic_songs)
    object AlbumScreen : Screen("Albums", "Albums", R.drawable.ic_album)
    object ArtistsScreen : Screen("Artist", "Artist", R.drawable.ic_artist)
    object PlayListScreen : Screen("Playlists", "Playlists", R.drawable.ic_playlists)

    companion object {
        fun toList(): List<Screen> {
            return listOf(HomeScreen,
//                SongsScreen ,
        AlbumScreen, ArtistsScreen, PlayListScreen)
        }


        fun getScreenFromRoute(route: String?): Screen? {
            //TODO("update this later")
            return when (route) {
                HomeScreen.route -> HomeScreen
//                SongsScreen.route -> SongsScreen
                AlbumScreen.route -> AlbumScreen
                ArtistsScreen.route -> ArtistsScreen
                PlayListScreen.route -> PlayListScreen
                else -> null

            }
        }
    }
}