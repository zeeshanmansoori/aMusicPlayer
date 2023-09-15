package com.zee.amusicplayer.domain.repository

import android.database.Cursor
import org.json.JSONObject


interface SongRepository {
    fun songs(): List<JSONObject>

    fun songs(cursor: Cursor?): List<JSONObject>

    fun songs(query: String): List<JSONObject>

    fun songsByFilePath(filePath: String): List<JSONObject>

    fun song(cursor: Cursor?): JSONObject?

    fun song(songId: Long): JSONObject?

    fun makeSongCursor(
        selection: String?,
        selectionValues: Array<String>?,
        sortOrder: String
    ): Cursor?

    fun getSongFromCursorImpl(
        cursor: Cursor
    ): JSONObject?
}
