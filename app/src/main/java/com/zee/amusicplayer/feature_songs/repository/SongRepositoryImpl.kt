package com.zee.amusicplayer.feature_songs.repository

import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC
import com.zee.amusicplayer.domain.repository.SongRepository
import com.zee.amusicplayer.feature_songs.data_source.AudioOfflineDataSource
import com.zee.amusicplayer.utils.SortOrder
import com.zee.amusicplayer.utils.log
import org.json.JSONObject

class SongRepositoryImpl(private val dataSource: AudioOfflineDataSource) : SongRepository {

    override fun songs(): List<JSONObject> {
        return songs(makeSongCursor(null, null, SortOrder.SongSortOrder.SONG_A_Z))
    }


    override fun songs(cursor: Cursor?): List<JSONObject> {

        val songs = arrayListOf<JSONObject>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val song =getSongFromCursorImpl(cursor)
                songs.add(song)

            } while (cursor.moveToNext())
        }
        cursor?.close()

        return songs
    }

    override fun songs(query: String): List<JSONObject> {

        return songs(

            makeSongCursor(
                MediaStore.Audio.AudioColumns.TITLE + " LIKE ?",
                arrayOf("%$query%"),
                sortOrder = SortOrder.SongSortOrder.SONG_A_Z
            )
        )
    }

    override fun songsByFilePath(filePath: String): List<JSONObject> {
        return songs(
            makeSongCursor(
                MediaStore.Audio.AudioColumns.DATA + "=?",
                arrayOf(filePath),
                sortOrder = SortOrder.SongSortOrder.SONG_A_Z
            )
        )
    }

    override fun song(cursor: Cursor?): JSONObject? {
        log("song(cursor) called")
        return cursor?.let { getSongFromCursorImpl(it) }
    }

    override fun song(songId: Long): JSONObject? {
        return song(
            makeSongCursor(
                MediaStore.Audio.AudioColumns._ID + "=?",
                arrayOf(songId.toString()),
                SortOrder.SongSortOrder.SONG_A_Z
            )
        )
    }


    override fun makeSongCursor(
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
            log("exception $ex")
            return null
        }
    }


    override fun getSongFromCursorImpl(
        cursor: Cursor
    ): JSONObject {
        return dataSource.getSongFromCursor(cursor)
    }
}