package com.zee.amusicplayer.domain.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.media3.common.MediaItem
import org.json.JSONObject


@SuppressLint("Range")
internal fun Cursor.getInt(columnName: String): Int {
    try {
        return this.getInt(this.getColumnIndex(columnName))
    } catch (ex: Throwable) {
        throw IllegalStateException("invalid column $columnName", ex)
    }
}

@SuppressLint("Range")
internal fun Cursor.getLong(columnName: String, default: Long = -1): Long {
    return try {
        this.getLong(this.getColumnIndex(columnName))
    } catch (ex: Exception) {
        ex.printStackTrace()
        default
    }
}

@SuppressLint("Range")
internal fun Cursor.getStringOrNull(columnName: String): String? {
    return try {
        this.getString(this.getColumnIndex(columnName))
    } catch (ex: Exception) {
        null
    }
}


internal fun JSONObject.getStringSafely(name: String): String {
    return try {
        getString(name)
    } catch (tgi: Exception) {
//        e.printStackTrace()
        ""
    }
}


/**
 * Returns the index of the mediaItem within player, default value is -1
 * */
internal var MediaItem.itemIndex
    set(value) {
        this.mediaMetadata.extras?.putInt("itemIndex", value)
    }
    get() = this.mediaMetadata.extras?.getInt("itemIndex") ?: -1


internal var MediaItem.dateModified
    set(value) {
        this.mediaMetadata.extras?.putLong("dateModified", value?:0L)
    }
    get() = this.requestMetadata.extras?.getLong("dateModified")


fun Context.getBitmapFromContentUri(contentUri: String?): Bitmap? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null

    return try {
        contentResolver.loadThumbnail(Uri.parse(contentUri), Size(500, 500), null)
    } catch (e: Exception) {
        null
    }
}

//==========================================
var JSONObject.id: String
    get() = this.getStringSafely("id")
    set(value) {
        this.put("id", value)
    }

var JSONObject.title: String
    get() = this.getStringSafely("title")
    set(value) {
        this.put("title", value)
    }

var JSONObject.albumName: String
    get() = this.getStringSafely("albumName")
    set(value) {
        this.put("albumName", value)
    }

var JSONObject.albumId: String
    get() = this.getStringSafely("albumId")
    set(value) {
        this.put("albumId", value)
    }

var JSONObject.artistId: String
    get() = this.getStringSafely("artistId")
    set(value) {
        this.put("artistId", value)
    }
var JSONObject.artistName: String
    get() = this.getStringSafely("artistName")
    set(value) {
        this.put("artistName", value)
    }
var JSONObject.albumCoverUri: String
    get() = this.getStringSafely("artUri")
    set(value) {
        this.put("artUri", value)
    }

var JSONObject.contentUri: String
    get() = this.getStringSafely("source")
    set(value) {
        this.put("source", value)
    }

var JSONObject.genre: String
    get() = this.getStringSafely("genre")
    set(value) {
        this.put("genre", value)
    }

var JSONObject.dateModified: Long
    get() = this.getLong("dateModified")
    set(value) {
        this.put("dateModified", value)
    }