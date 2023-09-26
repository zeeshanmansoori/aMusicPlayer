package com.zee.amusicplayer.dataSource

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.zee.amusciplayer.utils.SortOrder
import com.zee.amusciplayer.utils.getInt
import com.zee.amusciplayer.utils.getLong
import com.zee.amusciplayer.utils.getStringOrNull
import org.json.JSONObject


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
    )


    fun getSongFromCursor(cursor: Cursor): JSONObject {
        //log("getSongFromCursor cursor called from source $cursor")
        val id = cursor.getLong(MediaStore.Audio.AudioColumns._ID)
        val title = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.TITLE)
        val trackNumber = cursor.getInt(MediaStore.Audio.AudioColumns.TRACK)
        val year = cursor.getInt(MediaStore.Audio.AudioColumns.YEAR)
        val duration = cursor.getLong(MediaStore.Audio.AudioColumns.DURATION)
        val data = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.DATA)
        val dateModified = cursor.getLong(MediaStore.Audio.AudioColumns.DATE_MODIFIED)
        val albumId = cursor.getLong(MediaStore.Audio.AudioColumns.ALBUM_ID)
        val albumName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ALBUM)
        val artistId = cursor.getLong(MediaStore.Audio.AudioColumns.ARTIST_ID)
        val artistName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ARTIST)
        val genre = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            cursor.getStringOrNull(MediaStore.Audio.AudioColumns.GENRE)
        } else {
            ""
        }
        val composer = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.COMPOSER)
        val contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
        val albumUri =
            if (artistName == null) null else getMediaStoreAlbumCoverUri(albumId).toString()

        val jsonObject = JSONObject()
        jsonObject.put("id", id.toString())
        jsonObject.put("album", albumName.toString())
        jsonObject.put("title", title)
        jsonObject.put("artist", artistName.toString())
        jsonObject.put("genre", genre)
        jsonObject.put("source", contentUri)
        jsonObject.put("image", albumUri)
        return jsonObject
    }


    private fun getMediaStoreAlbumCoverUri(albumId: Long): Uri {
        val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
        return ContentUris.withAppendedId(sArtworkUri, albumId)
    }


    fun getCursor(
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String = SortOrder.SongSortOrder.SONG_A_Z
    ): Cursor? {

        return context.contentResolver.query(
            uri,
            projection, selection, selectionArgs, sortOrder
        )
    }

}