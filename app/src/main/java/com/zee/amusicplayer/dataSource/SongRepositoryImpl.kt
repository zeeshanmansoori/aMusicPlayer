package com.zee.amusicplayer.dataSource

import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC
import com.zee.amusciplayer.utils.SortOrder
import org.json.JSONObject

class SongRepositoryImpl(private val dataSource: AudioOfflineDataSource)  {

     fun songs(): List<JSONObject> {
        return songs(makeSongCursor(null, null, SortOrder.SongSortOrder.SONG_A_Z))
    }


     fun songs(cursor: Cursor?): List<JSONObject> {

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

     fun songs(query: String): List<JSONObject> {

        return songs(

            makeSongCursor(
                MediaStore.Audio.AudioColumns.TITLE + " LIKE ?",
                arrayOf("%$query%"),
                sortOrder = SortOrder.SongSortOrder.SONG_A_Z
            )
        )
    }

     fun songsByFilePath(filePath: String): List<JSONObject> {
        return songs(
            makeSongCursor(
                MediaStore.Audio.AudioColumns.DATA + "=?",
                arrayOf(filePath),
                sortOrder = SortOrder.SongSortOrder.SONG_A_Z
            )
        )
    }

     fun song(cursor: Cursor?): JSONObject? {

        return cursor?.let { getSongFromCursorImpl(it) }
    }

     fun song(songId: Long): JSONObject? {
        return song(
            makeSongCursor(
                MediaStore.Audio.AudioColumns._ID + "=?",
                arrayOf(songId.toString()),
                SortOrder.SongSortOrder.SONG_A_Z
            )
        )
    }


     fun makeSongCursor(
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


     fun getSongFromCursorImpl(
        cursor: Cursor
    ): JSONObject {
        return dataSource.getSongFromCursor(cursor)
    }
}