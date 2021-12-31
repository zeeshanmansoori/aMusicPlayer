package com.zee.amusicplayer.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.zee.amusicplayer.R
import com.zee.amusicplayer.utils.Constants


@Composable
fun MusicImage(
    modifier: Modifier = Modifier,
    cornerSize: Dp = Constants.rectanglesCorner,
    iconUrl: String? = null

) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerSize))
            .background(color = Color.LightGray)
            .padding(if (iconUrl.isNullOrEmpty()) 10.dp else 0.dp),
        contentAlignment = Alignment.Center
    ) {

        GlideImage(
            imageModel = iconUrl,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.FillBounds,
            // shows an image with a circular revealed animation.
            circularReveal = CircularReveal(duration = 250),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = painterResource(id = R.drawable.ic_songs),
            // shows an error ImageBitmap when the request failed.
            error = painterResource(id = R.drawable.ic_songs)
        )
    }


}
