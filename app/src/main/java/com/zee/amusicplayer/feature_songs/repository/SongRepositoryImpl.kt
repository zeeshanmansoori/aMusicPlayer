package com.zee.amusicplayer.feature_songs.repository

import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.feature_songs.data_source.AudioOfflineDataSource

class SongRepositoryImpl(private val dataSource: AudioOfflineDataSource) : SongRepository {

    override suspend fun getAllAudio(): List<SongItem> {
        return dataSource.allMusic()
    }

    override suspend fun search(key: String): List<SongItem> {
        return emptyList()
    }

}