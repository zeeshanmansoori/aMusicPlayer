package com.zee.amusicplayer.domain.useCase.artist

import com.zee.amusicplayer.domain.model.Artist
import com.zee.amusicplayer.presentation.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ArtistsUseCase(songsState: Flow<MainViewModel.SongsState>, scope: CoroutineScope) {
    val allArtists = songsState.map { state ->
        val songs = state.songs
        songs.groupBy {
            it.artistId to it.artistName
        }.map { pair ->
            Artist(id = pair.key.first, name = pair.key.second, songs = pair.value)
        }
    }.stateIn(scope, SharingStarted.WhileSubscribed(), emptyList())

}