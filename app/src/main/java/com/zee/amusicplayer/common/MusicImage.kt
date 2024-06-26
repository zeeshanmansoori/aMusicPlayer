package com.zee.amusicplayer.common

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.zee.amusicplayer.R
import com.zee.amusicplayer.domain.model.SongItem
import com.zee.amusicplayer.utils.Constants


@Composable
fun MusicImage(
    modifier: Modifier = Modifier,
    cornerSize: Dp = Constants.rectanglesCorner,
    song: SongItem,
) {

    val context = LocalContext.current
    val thumbnail: Bitmap? = getBitmapFromContentUri(context, song.contentUri)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerSize))
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center
    ) {

        AsyncImage(
            model = thumbnail,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(id = R.drawable.ic_songs),
            error = painterResource(id = R.drawable.ic_songs)
        )
    }


}


fun getBitmapFromContentUri(context: Context, contentUri: String?): Bitmap? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null

    return try {
        context.contentResolver.loadThumbnail(Uri.parse(contentUri ?: ""), Size(500, 500), null)
    } catch (e: Exception) {
        null
    }


}