package com.zee.amusicplayer.presentation.songs_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zee.amusicplayer.R
import com.zee.amusicplayer.common.MusicImage
import com.zee.amusicplayer.domain.model.SongItem

@Composable
fun SingleSongItem(
    modifier: Modifier = Modifier,
    song: SongItem,
    showEqualizer: Boolean = false,
) {
    Row(
        modifier = modifier
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MusicImage(modifier = Modifier.size(40.dp), song = song)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            Text(
                text = song.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = song.albumName,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (showEqualizer)
            Loader()

        IconButton(modifier = Modifier
            .size(22.dp)
            .padding(start = 5.dp), onClick = { }) {
            Icon(painter = painterResource(id = R.drawable.ic_more_vert), contentDescription = null)
        }


    }

}

//@Preview(showBackground = true)
//@Composable
//fun SingleItemPreview() {
//    SingleSongItem(song = SongItem(), showEqualizer = true)
//}

@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.equailizer))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        modifier = Modifier.size(25.dp),
        composition = composition,
        progress = progress,
    )
}
