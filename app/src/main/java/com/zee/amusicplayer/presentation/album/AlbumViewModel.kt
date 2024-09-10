package com.zee.amusicplayer.presentation.album

import androidx.lifecycle.ViewModel
import com.zee.amusicplayer.domain.model.Album
import com.zee.amusicplayer.domain.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlbumViewModel : ViewModel() {
    private val _allAlbums = MutableStateFlow(listOf<Album>())
    val allAlbums = _allAlbums.asStateFlow()

    fun setUpAlbum(songs: List<Song>) {
        _allAlbums.value = songs.groupBy {
            it.albumName
        }.map { pair ->
            Album(id = pair.key, title = pair.key, songs = pair.value)
        }
    }

}