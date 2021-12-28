package com.zee.amusicplayer.domain.use_cases

import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.domain.repository.SongRepository


class GetAllSongsUseCase(private val repository: SongRepository) {

    suspend operator fun invoke(): List<SongItem> {
        return repository.songs()
    }
}