package com.zee.amusicplayer.presentation.player_screen

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.zee.amusicplayer.R
import com.zee.amusicplayer.common.getBitmapFromContentUri
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
    val scaleFactor = animateFloatAsState(targetValue = if (doAnimate) 0.85f else 1f)

    val currentPlaying = songsVieModel.curPlayingSong.value
    val scope = rememberCoroutineScope()
    val playbackState = songsVieModel.playbackState.value
    val currentPosition = songsVieModel.curPlayerPosition.value
    val curSongDuration = songsVieModel.curSongDuration.value
    val context = LocalContext.current


    val thumbnail by remember {

        val result = derivedStateOf {
            val bm = getBitmapFromContentUri(
                context,
                songsVieModel.curPlayingSong.value?.description?.mediaUri?.toString()
            )
            bm
        }
        return@remember result
    }



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
                    .scale(scaleFactor.value)
                    .pointerInteropFilter {
                        doAnimate = if (it.action == MotionEvent.ACTION_DOWN) true
                        else if (it.action == MotionEvent.ACTION_UP || it.action == MotionEvent.ACTION_SCROLL) false
                        else false


                        true
                    },
                elevation = if (scaleFactor.value == 1f) 5.dp else 0.dp,
                shape = RoundedCornerShape(Constants.rectanglesCorner),
                color = Color.LightGray
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    AsyncImage(
                        model = thumbnail,
                        contentDescription=null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_songs),
                        error = painterResource(id = R.drawable.ic_songs),
                    )
                }
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
                playNext = { songsVieModel.skipToNextSong() },
                playPrevious = { songsVieModel.skipToPreviousSong() })

        }
    }
}


