package com.zee.amusicplayer.feature_albums.repository

import android.provider.MediaStore
import com.zee.amusicplayer.domain.model.AlbumItem
import com.zee.amusicplayer.domain.repository.AlbumRepository
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.utils.SortOrder

class AlbumRepositoryImpl(private val songRepository: SongRepository) : AlbumRepository {


    override suspend fun albums(): List<AlbumItem> {

        return emptyList()
    }

    override suspend fun albums(query: String): List<AlbumItem> {
        return emptyList()
    }

    override suspend fun album(albumId: Long): AlbumItem? {
        val cursor = songRepository.makeSongCursor(
            MediaStore.Audio.AudioColumns.ALBUM_ID + "=?",
            arrayOf(albumId.toString()),
            SortOrder.SongSortOrder.SONG_A_Z
        )
        return null


    }
}