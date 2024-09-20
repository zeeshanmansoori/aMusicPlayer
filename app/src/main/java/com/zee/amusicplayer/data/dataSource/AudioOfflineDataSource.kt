package com.zee.amusicplayer.data.dataSource

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import com.zee.amusicplayer.utils.MediaItemHelper
import com.zee.amusicplayer.utils.albumName
import com.zee.amusicplayer.utils.albumCoverUri
import com.zee.amusicplayer.utils.artistName
import com.zee.amusicplayer.utils.contentUri
import com.zee.amusicplayer.utils.dateModified
import com.zee.amusicplayer.utils.genre
import com.zee.amusicplayer.utils.getInt
import com.zee.amusicplayer.utils.getLong
import com.zee.amusicplayer.utils.getStringOrNull
import com.zee.amusicplayer.utils.id
import com.zee.amusicplayer.utils.title
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


    private fun getSongFromCursor(cursor: Cursor): JSONObject {
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
        val albumUri = if (artistName == null) null else getMediaStoreAlbumCoverUri(albumId).toString()

        val jsonObject = JSONObject()
        jsonObject.id = id.toString()
        jsonObject.title = title.toString()
        jsonObject.albumName =  albumName.toString()
        jsonObject.artistName =  artistName.toString()
        jsonObject.albumCoverUri =  albumUri.toString()
        jsonObject.dateModified =  dateModified
        jsonObject.contentUri =  contentUri.toString()
        jsonObject.genre =  genre.toString()
//        jsonObject.put("image", albumUri)
//        jsonObject.put("dateModified", dateModified)
        return jsonObject
    }


    private fun getMediaStoreAlbumCoverUri(albumId: Long): Uri {
        val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
        return ContentUris.withAppendedId(sArtworkUri, albumId)
    }


    private fun getCursor(
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String
    ): Cursor? {

        return context.contentResolver.query(
            uri,
            projection, selection, selectionArgs, sortOrder
        )
    }


    fun songs(): List<MediaItem> {

        val songs = arrayListOf<MediaItem>()
        val cursor = makeSongCursor(null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
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
            "${MediaStore.Audio.AudioColumns.IS_MUSIC} AND $selectionFinal"
        } else {
            MediaStore.Audio.AudioColumns.IS_MUSIC
        }


        selectionFinal =
            selectionFinal + " AND " + MediaStore.Audio.Media.DURATION + ">= " + 1000

        return try {
            getCursor(selectionFinal, selectionValues, sortOrder)
        } catch (ex: Exception) {
            return null
        }
    }


    private fun getSongFromCursorImpl(
        cursor: Cursor
    ): JSONObject {
        return getSongFromCursor(cursor)
    }

}