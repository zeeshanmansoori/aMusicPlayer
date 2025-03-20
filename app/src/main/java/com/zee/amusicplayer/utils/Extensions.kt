package com.zee.amusicplayer.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Size
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import com.zee.amusicplayer.data.db.entity.OtherMediaMetaData
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffoldState.currentFraction(sheetPeekHeight: Dp, btmNavBarHeight: Dp): Float {


    val targetValue = bottomSheetState.targetValue
    val currentValue = bottomSheetState.currentValue

    val density = LocalDensity.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val sheetPeekHeightPx = with(density) { sheetPeekHeight.toPx() }
    val btmNavBarHeightPx = with(density) { btmNavBarHeight.toPx() }
    val screenHeightPx = with(density) { (screenHeight).toPx() }

    val sheetOffset = try {
        bottomSheetState.requireOffset() + sheetPeekHeightPx + btmNavBarHeightPx
    } catch (e: Exception) {
        0f
    }

    val fraction = (sheetOffset / screenHeightPx).coerceIn(0f, 1f)
    var visibilityFraction = when {
        currentValue == SheetValue.Hidden && targetValue == SheetValue.Hidden -> 0f
        currentValue == SheetValue.Expanded && targetValue == SheetValue.Expanded -> 0f
        currentValue == SheetValue.PartiallyExpanded && targetValue == SheetValue.PartiallyExpanded -> 1f
        else -> fraction
    }

    visibilityFraction = (visibilityFraction * 100).toInt() / 100f
    Log.d(
        "zeeshan",
        "currentFraction: $visibilityFraction sheetOffset $sheetOffset " +
                "screenHeight $screenHeightPx c $currentValue t $targetValue " +
                "sheetPeekHeightPx $sheetPeekHeightPx"
    )

    return visibilityFraction

}

@Composable
fun rememberSystemGesturesBottomHeight(): Float {
    val context = LocalContext.current
    val density = LocalDensity.current

    return remember {
        var heightPx = 0f
        val windowInsets = ViewCompat.getRootWindowInsets((context as Activity).window.decorView)
        windowInsets?.let {

            heightPx = it.getInsets(WindowInsetsCompat.Type.systemGestures()).bottom.toFloat()
        }
        with(density) { heightPx }
    }
}

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
internal var MediaItem.fixedItemIndex
    set(value) {
        this.mediaMetadata.extras?.putInt("itemIndex", value)
    }
    get() = this.mediaMetadata.extras?.getInt("itemIndex") ?: -1


internal var MediaItem.dateModified
    set(value) {
        this.mediaMetadata.extras?.putLong("dateModified", value)
    }
    get() = this.mediaMetadata.extras?.getLong("dateModified") ?: 0L


var MediaItem.otherMediaMetaData: OtherMediaMetaData?
    set(value) {
        this.mediaMetadata.extras?.putParcelable("otherMetaData", value)
    }
    get() = mediaMetadata.extras?.getParcelable("otherMetaData", OtherMediaMetaData::class.java)

fun Context.getBitmapFromContentUri(contentUri: String?): Bitmap? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null

    return try {
        contentResolver.loadThumbnail(Uri.parse(contentUri), Size(500, 500), null)
    } catch (e: Exception) {
        null
    }
}
//==========================================

fun Long.toTwoDigitWord() = if (this >= 10) "$this" else "0$this"

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