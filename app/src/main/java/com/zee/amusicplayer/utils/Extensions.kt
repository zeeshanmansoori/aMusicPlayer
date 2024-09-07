package com.zee.amusicplayer.utils

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
        ex.printStackTrace()
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
        this.mediaMetadata.extras?.putString("dateModified", value)
    }
    get() = this.requestMetadata.extras?.getString("dateModified") ?: ""


fun Context.getBitmapFromContentUri(contentUri: Uri?): Bitmap? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null

    return try {
        contentResolver.loadThumbnail(contentUri!!, Size(500, 500), null)
    } catch (e: Exception) {
        null
    }
}