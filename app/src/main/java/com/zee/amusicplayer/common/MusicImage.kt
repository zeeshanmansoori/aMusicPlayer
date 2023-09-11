package com.zee.amusicplayer.common

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.zee.amusicplayer.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MusicImage(
    modifier: Modifier = Modifier,
    contentUri: String?,
) {

    val context = LocalContext.current
    val thumbnail = remember {
        mutableStateOf<Bitmap?>(null)
    }


    LaunchedEffect(key1 = contentUri) {
        launch(Dispatchers.IO) {
            thumbnail.value = getBitmapFromContentUri(context, contentUri)
        }
    }

    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {

        if (thumbnail.value != null) {
            GlideImage(
                model = thumbnail.value,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        } else Image(
            painter = painterResource(id = R.drawable.ic_songs),
            contentDescription = "",
        )
    }


}


suspend fun getBitmapFromContentUri(context: Context, contentUri: String?): Bitmap? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return null

    return try {
        context.contentResolver.loadThumbnail(Uri.parse(contentUri ?: ""), Size(500, 500), null)
    } catch (e: Exception) {
        null
    }


}