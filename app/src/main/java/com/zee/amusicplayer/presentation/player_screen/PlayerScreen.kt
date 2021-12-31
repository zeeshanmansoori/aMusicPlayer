package com.zee.amusicplayer.presentation.player_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zee.amusicplayer.R
import com.zee.amusicplayer.presentation.player_screen.component.BottomControllerBar
import com.zee.amusicplayer.presentation.player_screen.component.PlayerControllerBar
import com.zee.amusicplayer.presentation.player_screen.component.TrackBar
import com.zee.amusicplayer.presentation.songs_screen.SongsVieModel
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MarqueeText
import com.zee.amusicplayer.utils.log
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    songsVieModel: SongsVieModel,
    visiblity: Float = 1f,
    bottomSheetState: BottomSheetScaffoldState,
) {


    val currentPlaying = songsVieModel.curPlayingSong.value
    val isPlaying = songsVieModel.isCurrentSongPLaying.value
    val scope = rememberCoroutineScope()
    val playbackState = songsVieModel.playbackState.value
    var currentPosition = songsVieModel.curPlayerPosition.value
    val curSongDuration = songsVieModel.curSongDuration.value


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.surface
            )
            .padding(horizontal = Constants.paddingStart)
    ) {

        Column(
            modifier
                .fillMaxSize()
                .alpha(visiblity),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                elevation = 5.dp,
                shape = RoundedCornerShape(Constants.rectanglesCorner),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                color = Color.LightGray

            ) {

                Image(
                    modifier = modifier
                        .background(color = Color.LightGray)
                        .padding(80.dp),
                    painter = painterResource(id = R.drawable.ic_songs),
                    contentDescription = null
                )

            }

            LaunchedEffect(key1 = currentPosition) {
                log("progress $currentPosition")
                log("max $curSongDuration")
            }

            TrackBar(
                modifier = Modifier.padding(vertical = 10.dp),
                progress = if (curSongDuration != 0L && curSongDuration < currentPosition) 0L else currentPosition,
                max = if (curSongDuration != 0L && curSongDuration < currentPosition) 1L else curSongDuration,
                updateProgress = { }

            )
            MarqueeText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                text = currentPlaying?.description?.title.toString(),
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp)
            )

            Text(
                text = currentPlaying?.description?.subtitle.toString(),
                Modifier.padding(vertical = 30.dp)
            )

            PlayerControllerBar(
                isPlaying = isPlaying,
                onPlayingStateChange = {
                    songsVieModel.playOrToggleSong(currentPlaying?.description?.mediaId, true)
                    songsVieModel.isCurrentSongPLaying.value = it
                },
                playNext = { songsVieModel.skipToNextSong() },
                playPrevious = { songsVieModel.skipToPreviousSong() })

        }

        BottomControllerBar(modifier = Modifier.align(Alignment.BottomCenter)) {

            // dismiss the bottomsheet
            if (bottomSheetState.bottomSheetState.isExpanded)
                scope.launch {
                    bottomSheetState.bottomSheetState.collapse()
                }


        }

    }
}


