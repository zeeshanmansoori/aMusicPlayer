package com.zee.amusicplayer.domain.repository

import androidx.media3.common.MediaItem

interface ISongRepository {
    fun getRootItem(): MediaItem
    fun getMediaItem(id: String): MediaItem
    fun getMediaItems(): List<MediaItem>

    suspend fun updateDataBaseWithMetaData(): Int
    suspend fun updateMediaTree()


}