package com.zee.amusicplayer.feature_songs.data_source

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.utils.getInt
import com.zee.amusicplayer.utils.getLong
import com.zee.amusicplayer.utils.getString
import com.zee.amusicplayer.utils.getStringOrNull
import java.util.concurrent.TimeUnit


class AudioOfflineDataSource(private val context: Context) {

    private val uri = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    else MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    private val projection = arrayOf(
        MediaStore.Audio.AudioColumns._ID, // 0
        MediaStore.Audio.AudioColumns.TITLE, // 1
        MediaStore.Audio.AudioColumns.TRACK, // 2
        MediaStore.Audio.AudioColumns.YEAR, // 3
        MediaStore.Audio.AudioColumns.DURATION, // 4
        MediaStore.Audio.AudioColumns.DATA, // 5
        MediaStore.Audio.AudioColumns.DATE_MODIFIED, // 6
        MediaStore.Audio.AudioColumns.ALBUM_ID, // 7
        MediaStore.Audio.AudioColumns.ALBUM, // 8
        MediaStore.Audio.AudioColumns.ARTIST_ID, // 9
        MediaStore.Audio.AudioColumns.ARTIST, // 10
        MediaStore.Audio.AudioColumns.COMPOSER, // 11
        "album_artist" // 12
    )

    private val selection =
        MediaStore.Audio.Media.IS_MUSIC + " AND " + MediaStore.Audio.Media.DURATION + ">=?"

    private val selectionArgs =
        arrayOf(TimeUnit.MILLISECONDS.convert(2, TimeUnit.MINUTES).toString())

    private val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"


    suspend fun allMusic(): MutableList<SongItem> {
        val ls = mutableListOf<SongItem>()
        context.contentResolver.query(
            uri,
            projection, selection, selectionArgs, sortOrder
        )?.use { cursor ->

            if (!cursor.moveToFirst()) return@use

            do {

                val id = cursor.getLong(MediaStore.Audio.AudioColumns._ID)
                val title = cursor.getString(MediaStore.Audio.AudioColumns.TITLE)
                val trackNumber = cursor.getInt(MediaStore.Audio.AudioColumns.TRACK)
                val year = cursor.getInt(MediaStore.Audio.AudioColumns.YEAR)
                val duration = cursor.getLong(MediaStore.Audio.AudioColumns.DURATION)
                val data = cursor.getString(MediaStore.Audio.AudioColumns.DATA)
                val dateModified = cursor.getLong(MediaStore.Audio.AudioColumns.DATE_MODIFIED)
                val albumId = cursor.getLong(MediaStore.Audio.AudioColumns.ALBUM_ID)
                val albumName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ALBUM)
                val artistId = cursor.getLong(MediaStore.Audio.AudioColumns.ARTIST_ID)
                val artistName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ARTIST)
                val composer = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.COMPOSER)
                val albumArtist = cursor.getStringOrNull("album_artist")

                ls.add(
                    SongItem(
                        id,
                        title,
                        trackNumber,
                        year,
                        duration,
                        data,
                        dateModified,
                        albumId,
                        albumName ?: "",
                        artistId,
                        artistName ?: "",
                        composer ?: "",
                        albumArtist ?: ""
                    )
                )
            } while (cursor.moveToNext())


            cursor.close()
        }

        return ls
    }


    private fun getThumbnail(albumId: Long): Uri {
        val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
        return ContentUris.withAppendedId(sArtworkUri, albumId)
    }

}