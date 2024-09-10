package com.zee.amusicplayer.presentation.common

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.zee.amusicplayer.utils.getBitmapFromContentUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MusicImage(
    modifier: Modifier = Modifier,
    artUri: String? = null,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val thumbnail = remember(artUri) {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(key1 = artUri) {
        scope.launch(Dispatchers.IO) {
            thumbnail.value = context.getBitmapFromContentUri(artUri)
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        if (thumbnail.value == null) {
            PlaceHolder()
            return@Box
        }

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
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