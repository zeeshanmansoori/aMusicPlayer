package com.zee.amusicplayer.domain.use_cases

import com.zee.amusicplayer.domain.model.ArtistItem
import com.zee.amusicplayer.domain.repository.ArtistRepository


class GetAllArtistUseCase(private val repository: ArtistRepository) {

    suspend operator fun invoke(): List<ArtistItem> {
        return repository.albumArtists()
    }
}