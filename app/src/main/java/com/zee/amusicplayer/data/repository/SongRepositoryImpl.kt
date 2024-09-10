package com.zee.amusicplayer.data.repository

import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC
import androidx.media3.common.MediaItem
import com.zee.amusicplayer.data.dataSource.AudioOfflineDataSource
import com.zee.amusicplayer.domain.repository.ISongRepository
import com.zee.amusicplayer.presentation.utils.SortOrder
import com.zee.amusicplayer.utils.MediaItemHelper
import org.json.JSONObject

class SongRepositoryImpl(private val dataSource: AudioOfflineDataSource) : ISongRepository {

    private val songs = mutableMapOf<String, MediaItem>()

    override fun getRootItem(): MediaItem {
        return MediaItemHelper.Root
    }

    override fun getSong(id: String): MediaItem {
        return songs[id] ?: MediaItemHelper.Root
    }

    override fun getSongs(): List<MediaItem> {
        songs.clear()
        val newSongs = songs(makeSongCursor(null, null, SortOrder.SongSortOrder.SONG_A_Z))
        newSongs.map {
            songs[it.mediaId] = it
        }

        return newSongs
    }


    private fun songs(cursor: Cursor?): List<MediaItem> {

        val songs = arrayListOf<MediaItem>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val song = MediaItemHelper.buildMediaItem(getSongFromCursorImpl(cursor))
                songs.add(song)

            } while (cursor.moveToNext())
        }
        cursor?.close()

        return songs
    }

    private fun makeSongCursor(
        selection: String?,
        selectionValues: Array<String>?,
        sortOrder: String
    ): Cursor? {

        var selectionFinal = selection
        selectionFinal = if (selection != null && selection.trim { it <= ' ' } != "") {
            "$IS_MUSIC AND $selectionFinal"
        } else {
            IS_MUSIC
        }


        selectionFinal =
            selectionFinal + " AND " + MediaStore.Audio.Media.DURATION + ">= " + 1000

        return try {
            dataSource.getCursor(selectionFinal, selectionValues, sortOrder)
        } catch (ex: Exception) {
            return null
        }
    }


    private fun getSongFromCursorImpl(
        cursor: Cursor
    ): JSONObject {
        return dataSource.getSongFromCursor(cursor)
    }

}