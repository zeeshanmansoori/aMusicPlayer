package com.zee.amusicplayer.domain.useCase.album

import androidx.navigation.NavHostController
import com.zee.amusicplayer.domain.model.Album
import com.zee.amusicplayer.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AlbumUseCase(
    songsState: Flow<MainViewModel.SongsState>,
    scope: CoroutineScope
) {

    val allAlbums = songsState.map { state ->
        val songs = state.songs
        songs.groupBy {
            it.albumName
        }.map { pair ->
            Album(id = pair.key, title = pair.key, songs = pair.value)
        }
    }.stateIn(scope, SharingStarted.WhileSubscribed(), emptyList())


}