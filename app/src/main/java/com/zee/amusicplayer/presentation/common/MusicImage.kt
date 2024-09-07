package com.zee.amusicplayer.presentation.common

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.zee.amusicplayer.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MusicImage(
    modifier: Modifier = Modifier,
    artUri: Uri? = null,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val thumbnail = remember {
        mutableStateOf<Bitmap?>(null)
    }


    DisposableEffect(key1 = artUri) {
        scope.launch(Dispatchers.IO) {
            thumbnail.value = getBitmapFromContentUri(context, artUri)
        }
        onDispose {
            thumbnail.value = null
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        if (thumbnail.value == null) {
            PlaceHolder()
            return@Box
        }

        AsyncImage(
            model = thumbnail.value,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }


}

@Composable
fun PlaceHolder() {

    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_songs),
            contentDescription = "",
        )
    }
}

fun getBitmapFromContentUri(context: Context, contentUri: Uri?): Bitmap? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null

    return try {
        context.contentResolver.loadThumbnail(contentUri!!, Size(500, 500), null)
    } catch (e: Exception) {
        null
    }


}