package com.zee.amusicplayer.domain.repository

import com.zee.amusicplayer.domain.model.SongItem

interface SongRepository {
    suspend fun getAllAudio(): List<SongItem>
    suspend fun search(key: String): List<SongItem>
}