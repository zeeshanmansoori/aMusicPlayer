package com.zee.amusicplayer.domain.repository

import com.zee.amusicplayer.domain.model.AlbumItem

interface AlbumRepository {
    suspend fun albums(): List<AlbumItem>
    suspend fun albums(query: String): List<AlbumItem>
    suspend fun album(albumId: Long): AlbumItem?
}