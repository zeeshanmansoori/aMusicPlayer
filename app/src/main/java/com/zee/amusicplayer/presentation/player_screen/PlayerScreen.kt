package com.zee.amusicplayer.presentation.player_screen

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.zee.amusicplayer.common.MusicImage
import com.zee.amusicplayer.exo_player.isPlaying
import com.zee.amusicplayer.presentation.player_screen.component.PlayerControllerBar
import com.zee.amusicplayer.presentation.player_screen.component.TrackBar
import com.zee.amusicplayer.presentation.songs_screen.SongsVieModel
import com.zee.amusicplayer.utils.Constants
import com.zee.amusicplayer.utils.MarqueeText
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    songsVieModel: SongsVieModel,
    visibility: Float = 1f,
    bottomSheetState: BottomSheetScaffoldState,
) {

    var doAnimate by remember {
        mutableStateOf(false)
    }

    val scaleFactor by animateFloatAsState(targetValue = if (doAnimate) 0.85f else 1f, label = "")

    val currentPlaying = songsVieModel.curPlayingSong.value
    val scope = rememberCoroutineScope()
    val playbackState = songsVieModel.playbackState.value
    val currentPosition = songsVieModel.curPlayerPosition.value
    val curSongDuration = songsVieModel.curSongDuration.value

    BackHandler(bottomSheetState.bottomSheetState.isExpanded) {
        scope.launch {
            bottomSheetState.bottomSheetState.collapse()
        }

    }



    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.surface
            )
            .padding(horizontal = Constants.paddingStart)


    ) {

        Column(
            Modifier
                .fillMaxSize()
                .alpha(visibility),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .scale(scaleFactor),
                elevation = if (scaleFactor == 1f) 5.dp else 0.dp,
                shape = RoundedCornerShape(Constants.rectanglesCorner),
                color = Color.LightGray
            ) {

                MusicImage(
                    Modifier.fillMaxSize()
                        .pointerInteropFilter {
                            doAnimate = when (it.action) {
                                    MotionEvent.ACTION_DOWN -> true
                                    MotionEvent.ACTION_UP, MotionEvent.ACTION_SCROLL -> false
                                    else -> false
                                }

                            true
                        },
                    contentUri = songsVieModel.curPlayingSong.value?.description?.mediaUri?.toString(),
                )
            }

            TrackBar(
                modifier = Modifier.padding(vertical = 10.dp),
                progress = if (curSongDuration != 0L && curSongDuration < currentPosition) 0L else currentPosition,
                max = if (curSongDuration != 0L && curSongDuration < currentPosition) 1L else curSongDuration,
                seekTo = songsVieModel::seekTo
            )
            MarqueeText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                text = currentPlaying?.description?.title.toString(),
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp)
            )

            Text(
                text = currentPlaying?.description?.subtitle.toString(),
                Modifier.padding(vertical = 30.dp)
            )

            PlayerControllerBar(
                isPlaying = playbackState?.isPlaying == true,
                onPlayingStateChange = {
                    songsVieModel.playOrToggleSong(currentPlaying?.description?.mediaId, true)

                },
                playNext = songsVieModel::skipToNextSong,
                playPrevious =  songsVieModel::skipToPreviousSong,
            )

        }
    }
}


