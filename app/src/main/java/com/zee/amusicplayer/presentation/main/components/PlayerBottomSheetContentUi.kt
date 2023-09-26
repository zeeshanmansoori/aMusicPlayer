package com.zee.amusicplayer.presentation.main.components

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.zee.amusicplayer.presentation.common.MusicImage
import com.zee.amusicplayer.presentation.main.MainViewModel
import com.zee.amusicplayer.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun PlayerBottomSheetContentUi(
    modifier: Modifier = Modifier,
    visibility: Float = 1f,
    playerState: MainViewModel.PlayerState = MainViewModel.PlayerState.EMPTY,
    scope: CoroutineScope = rememberCoroutineScope(),
    onPlayPauseClick: () -> Unit = {},
    onNextButtonClick: () -> Unit = {},
    onPreviousButtonClick: () -> Unit = {},
    isBottomSheetExpanded: () -> Boolean = { true },
    onBackPress: () -> Unit = {},
    seekTo: (Long) -> Unit = {},
) {

    val item = playerState.item
    val duration = playerState.duration
    val progress = playerState.progress

    var doAnimate by remember {
        mutableStateOf(false)
    }

    val scaleFactor by animateFloatAsState(targetValue = if (doAnimate) 0.85f else 1f, label = "")

    BackHandler(isBottomSheetExpanded.invoke()) {
        scope.launch {
            onBackPress.invoke()
        }

    }



    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.surface
            )
            .padding(horizontal = Constants.SIDE_PADDING)


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
                    Modifier
                        .fillMaxSize()
                        .pointerInteropFilter {
                            doAnimate = when (it.action) {
                                MotionEvent.ACTION_DOWN -> true
                                MotionEvent.ACTION_UP, MotionEvent.ACTION_SCROLL -> false
                                else -> false
                            }

                            true
                        },
                    artUri = item?.requestMetadata?.mediaUri,
                )
            }

            TrackBar(
                modifier = Modifier.padding(vertical = 10.dp),
                progress = if (duration != 0L && duration < progress) 0L else progress,
                max = if (duration != 0L && duration < progress) 1L else duration,
                seekTo = seekTo
            )
            Text(
                modifier = Modifier
                    .basicMarquee()
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                text = item?.mediaMetadata?.title.toString(),
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp)
            )

            Text(
                text = item?.mediaMetadata?.artist.toString(),
                Modifier.padding(vertical = 30.dp)
            )

            PlayerControllerBar(
                isPlaying = playerState.isPlaying,
                onPlayingStateChange = onPlayPauseClick,
                playNext = onNextButtonClick,
                playPrevious = onPreviousButtonClick,
            )

        }
    }
}
