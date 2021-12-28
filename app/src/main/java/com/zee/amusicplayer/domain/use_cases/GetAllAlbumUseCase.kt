package com.zee.amusicplayer.domain.use_cases

import com.zee.amusicplayer.domain.model.AlbumItem
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.domain.repository.AlbumRepository


class GetAllAlbumUseCase(private val repository: AlbumRepository) {

    suspend operator fun invoke(): List<AlbumItem> {
        return repository.albums()
    }
}