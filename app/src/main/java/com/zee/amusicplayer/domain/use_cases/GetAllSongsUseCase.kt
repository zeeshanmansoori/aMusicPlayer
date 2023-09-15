package com.zee.amusicplayer.domain.use_cases

import com.zee.amusicplayer.domain.repository.SongRepository
import org.json.JSONObject


class GetAllSongsUseCase(private val repository: SongRepository) {

    suspend operator fun invoke(): List<JSONObject> {
        return repository.songs()
    }
}