package com.zee.amusicplayer.domain.repository

import android.database.Cursor
import com.zee.amusicplayer.domain.model.SongItem


interface SongRepository {
    fun songs(): List<SongItem>

    fun songs(cursor: Cursor?): List<SongItem>

    fun songs(query: String): List<SongItem>

    fun songsByFilePath(filePath: String): List<SongItem>

    fun song(cursor: Cursor?): SongItem?

    fun song(songId: Long): SongItem?

    fun makeSongCursor(
        selection: String?,
        selectionValues: Array<String>?,
        sortOrder: String
    ): Cursor?

    fun getSongFromCursorImpl(
        cursor: Cursor
    ): SongItem?
}
