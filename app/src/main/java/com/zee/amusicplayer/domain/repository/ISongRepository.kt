package com.zee.amusicplayer.domain.repository

import androidx.media3.common.MediaItem

interface ISongRepository {
    fun getRootItem(): MediaItem
    fun getSong(id: String): MediaItem
    fun getSongs(): List<MediaItem>

}